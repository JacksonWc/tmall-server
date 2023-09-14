package cn.tedu.tmall.passport.service.impl;


import cn.tedu.tmall.passport.dao.cache.IUserCacheRepository;
import cn.tedu.tmall.passport.dao.persist.repository.ILoginLogRepository;
import cn.tedu.tmall.passport.dao.persist.repository.IUserRepository;
import cn.tedu.tmall.passport.pojo.entity.LoginLog;
import cn.tedu.tmall.passport.pojo.param.UserLoginInfoParam;
import cn.tedu.tmall.passport.pojo.vo.UserLoginInfoVO;
import cn.tedu.tmall.passport.pojo.vo.UserLoginResultVO;
import cn.tedu.tmall.passport.service.IUserService;
import com.alibaba.fastjson.JSON;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.po.UserStatePO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Value("${tmall.jwt.secret-key}")
    private String secretKey;
    @Value("${tmall.jwt.duration-in-minute}")
    private long durationInMinute;

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserCacheRepository userCacheRepository;
    @Autowired
    private ILoginLogRepository loginLogRepository;

    @Override
    public UserLoginResultVO login(UserLoginInfoParam userLoginInfoParam, String remoteAddr, String userAgent) {
        // 从参数中取出username
        // 调用repository，根据用户名查询用户数据
        // 判断查询结果是否为null
        // 是：抛出异常（用户名不存在）
        String username = userLoginInfoParam.getUsername();
        UserLoginInfoVO loginInfo = userRepository.getLoginInfoByUsername(username);
       // System.out.println("UserLoginInfoVO:"+loginInfo);
        if (loginInfo == null) {
            String message = "登录失败，用户名或密码错误！";
            throw new ServiceException(ServiceCode.ERROR_UNAUTHORIZED, message);
        }

        // 判断查询结果中的账号状态（enable）是否“不处于”正常状态
        // 是：抛出异常（账号状态异常）
        if (loginInfo.getEnable() != 1) {
            String message = "登录失败，此账号已经被禁用！";
            throw new ServiceException(ServiceCode.ERROR_UNAUTHORIZED_DISABLED, message);
        }

        // 【注意】由于暂时还没有引入Security框架，则暂时无法处理BCrypt密文，需要暂时将数据表中各账号的密码暂时改为明文密码值
        // 判断查询结果中的密码与参数中的密码是否“不匹配”
        // 是：抛出异常（密码错误）
        String password = userLoginInfoParam.getPassword();
        //if (!loginInfo.getPassword().equals(password)) {
        if (!passwordEncoder.matches(password, loginInfo.getPassword())) {
            String message = "登录失败，用户名或密码错误！";
            throw new ServiceException(ServiceCode.ERROR_UNAUTHORIZED, message);
        }


        // 确定当事人
        CurrentPrincipal principal = new CurrentPrincipal();
        principal.setId(loginInfo.getId());
        principal.setUsername(loginInfo.getUsername());

        // 确定权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> permissions = loginInfo.getPermissions();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }


        //  将权限列表从List转换为JSON格式的字符串，再写入到Redis中，这里的做法是默认权限不改的做法
        // 将权限列表和用户状态写入到Redis中
        String authoritiesJsonString = JSON.toJSONString(authorities);
        UserStatePO userStatePO = new UserStatePO();
        userStatePO.setEnable(loginInfo.getEnable());
        userStatePO.setAuthoritiesJsonString(authoritiesJsonString);
        userCacheRepository.saveUserState(loginInfo.getId(), userStatePO);

//        // 向SecurityContext中存入Authentication
////        因为是在登录之后的认证所以第二个参数凭证（一般是密码）是空的
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                principal, null, authorities);
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);


        // TODO 处理登录日志相关：向登录日志表中插入数据，修改用户表中冗余存储的登录数据
        LocalDateTime now = LocalDateTime.now();
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(loginInfo.getId());
        loginLog.setUsername(username);
        loginLog.setIp(remoteAddr);
        loginLog.setUserAgent(userAgent);
        loginLog.setGmtLogin(now);
        int rows = loginLogRepository.insert(loginLog);
        if (rows != 1) {
            String message = "登录失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

//        修改用户表中冗余存储的登录数据,loginCount + 1没有必要写在方法上，意思是登录次数+1:为了repository少做一次查询可以做了
        rows = userRepository.updateLastLogin(loginInfo.getId(), loginInfo.getLoginCount()+1, remoteAddr, now);
        if (rows != 1) {
            String message = "登录失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }


//        生成jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginInfo.getId());
        claims.put("username", loginInfo.getUsername());
        claims.put("remote_addr", remoteAddr);
        claims.put("user_agent", userAgent);


        Date date = new Date(System.currentTimeMillis() + 1L*durationInMinute * 60 * 1000);
        String jwt = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload
                .setClaims(claims)
                .setExpiration(date)
                // Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 完成
                .compact();
        UserLoginResultVO loginResult = new UserLoginResultVO();
        loginResult.setId(loginInfo.getId());
        loginResult.setUsername(loginInfo.getUsername());
        loginResult.setAvatar(loginInfo.getAvatar());
        loginResult.setToken(jwt);
        loginResult.setAuthorities(permissions);
        return loginResult;


    }

    @Override
    public void logout(CurrentPrincipal currentPrincipal) {
        Long id = currentPrincipal.getId();
        userCacheRepository.deleteUserState(id);
    }

}
