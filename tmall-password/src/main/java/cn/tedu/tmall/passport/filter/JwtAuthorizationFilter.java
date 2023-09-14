package cn.tedu.tmall.passport.filter;


import cn.tedu.tmall.passport.dao.cache.IUserCacheRepository;
import com.alibaba.fastjson.JSON;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.po.UserStatePO;
import cn.tedu.tmall.common.web.JsonResult;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * <p>处理JWT的过滤器，主要职责：</p>
 * <ul>
 *     <li>尝试接收JWT</li>
 *     <li>尝试解析JWT</li>
 *     <li>将解析JWT得到的用户数据创建为Authorization并存入到SecurityContext</li>
 * </ul>
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Value("${tmall.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private IUserCacheRepository userCacheRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".css") || requestURI.endsWith(".js")) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(request.getRequestURI());
        System.out.println("JwtAuthorizationFilter.doFilterInternal()");

        // 尝试接收JWT
        String jwt = request.getHeader("Authorization");
        System.out.println("客户端携带的JWT：" + jwt);

        // 检查JWT的基本有效性
        if (!StringUtils.hasText(jwt)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }


        // 尝试解析JWT
        response.setContentType("application/json; charset=UTF-8");
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            String message = "您的登录信息已经过期，请重新登录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (SignatureException e) {
            String message = "非法访问！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (MalformedJwtException e) {
            String message = "非法访问！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_MALFORMED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (Throwable e) {
            String message = "服务器忙，请稍后再试！【开发阶段：同学们，当你看到这句话时，你应该通过服务器端的控制台了解本次出现异常的原因，并添加针对处理此异常的方法】";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNKNOWN, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }


        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        String jwtRemoteAddr = claims.get("remote_addr", String.class);
        String jwtUserAgent = claims.get("user_agent", String.class);

        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        if (!remoteAddr.equals(jwtRemoteAddr) && !userAgent.equals(jwtUserAgent)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从Redis中读取此用户的相关信息
        UserStatePO userState = userCacheRepository.getUserState(id);

        // 判断用户账号状态
        // 注意：在设计“禁用账号”时，需要删除此账号在Redis中对应的数据
        if (userState == null) {
            String message = "您的登录信息已经过期！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }

        if (userState.getEnable() == 0) {
            String message = "您的账号状态已经被禁用！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED_DISABLED, message);
            PrintWriter writer = response.getWriter();
            //Filter--可以使用response直接返回json格式的JsonResult结果，因为数据还没到controller
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }

        // 续期Redis中的用户状态信息
        userCacheRepository.expire(id);

        // 确定当事人
        CurrentPrincipal principal = new CurrentPrincipal();
        principal.setId(id);
        principal.setUsername(username);

        // 从Redis中读取此用户的权限列表,是来自于UserStatePO
        // 确定权限列表
        String authoritiesJsonString = userState.getAuthoritiesJsonString();
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesJsonString, SimpleGrantedAuthority.class);

        // 将解析JWT得到的用户数据创建为Authorization并存入到SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, null, authorities);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);


        // 放行，如果没有执行过滤器链的doFilter()则相当于“阻止”
        filterChain.doFilter(request, response);
    }

}
