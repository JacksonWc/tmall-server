# 关于Session

HTTP协议是无状态的协议，表现为当同一个客户端多次访问同一个服务器时，后续的访问过程中，服务器并不能识别出它就是此前来访的客户端。

为了识别客户端的身份，从编程技术上引入了Session机制，当某个客户端首次访问某个服务器时，服务器会在内存中准备一片存储空间，用于存放此客户端对应的数据，向客户端响应一个Session ID（本质上是一个UUID值），此Session ID也是访问内存中客户端数据的Key，后续，当客户端再次访问同一个服务器时，会自动携带此Session ID，也就可以根据Session ID找到在服务器端内存中对应的数据。

基于以上Session机制，可以选择在客户端的某些访问（例如登录并判定为成功）中，向Session中写入数据，后续，同一个客户端再次来访时，取出前序存入的数据。

Spring Security中的`SecurityContext`默认也是基于Session存储的！

由于Session是保存在服务器端的内存中的数据，所以，Session可能存在以下不足：

- 不适合存储大量的数据
  - 从编程风格或编码规范层面即可解决此问题
- 默认不适合于集群或分布式系统
  - 可以通过共享Session来解决此问题
- 不适合长时间保存客户端的身份和状态
  - 通常，Session的有效期会设置为15分钟或30分钟，某些场景也可能设置为其它值，例如网上银行系统可能会设置为更短的时间
  - 无解

# 关于Token

Token：票据 / 令牌

当某个客户端首次访问某个服务器时，服务器会向客户端响应一个Token，后续，当客户端再次访问同一个服务器时，会主动携带此Token，服务器即可根据Token识别客户端的身份。

与Session ID不同，Token自身是有意义的数据，则服务器并不需要在内存中记录用户的身份信息，只需要解读Token就可以识别客户端的身份！

由于使用Token机制后，服务器端并不需要在内存中记录数据，所以，Token可以长时间保存用户的身份信息！

# 关于JWT

**JWT**：**J**SON **W**eb **T**oken

JWT是一种使用JSON格式存储数据的、用于网络数据传输的Token。

JWT的数据表现是一个通过算法进行运算得到的结果，需要具有校验机制，避免JWT数据被篡改。

JWT官网：https://jwt.io

JWT数据大致如下：

![image-20230905101904147](assets/image-20230905101904147.png)

每个JWT数据都由3大部分组成：

- Header：头部信息，用于声明算法与Token类型
- Payload：载荷，用于存储数据（你希望存入到JWT中的数据）
- Verify Signature：验证签名

在项目中处理JWT时，需要添加依赖项，可以使用`jjwt`这个依赖项：

```xml
<jjwt.version>0.9.1</jjwt.version>
```

```xml
<!-- JJWT（Java JWT） -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>${jjwt.version}</version>
</dependency>
```

关于JWT的测试使用：

```java
package cn.tedu.tmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    String secretKey = "8KS7u4F9ufaJa7sPD3fDa";

    @Test
    void generate() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 9527);
        claims.put("username", "zhangsan");

        Date date = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);

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
        System.out.println(jwt);
    }

    @Test
    void parse() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTUyNywiZXhwIjoxNjk2NDc2MzA0LCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.2zaKfI4efoovcAVCgoyH3QIN0zdqcffcrTkW8sDSj2s";

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);

        System.out.println("id = " + id);
        System.out.println("username = " + username);
    }

}
```

在解析JWT时，如果JWT已经过期，则会抛出异常：

```
io.jsonwebtoken.ExpiredJwtException: JWT expired at 2023-09-05T11:12:37Z. Current time: 2023-09-05T11:15:01Z, a difference of 144156 milliseconds.  Allowed clock skew: 0 milliseconds.
```

如果解析时使用的`secretKey`与生成时的不同，或JWT数据有误无法通过签名的验证，将抛出异常：

```
io.jsonwebtoken.SignatureException: JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
```

如果JWT数据格式错误，将抛出异常：

```
io.jsonwebtoken.MalformedJwtException: Unable to read JSON value: {"id":952":1696476304,"username":"zhangsan"}
```

**注意：**即便不知道生成JWT时所使用的`secretKey`的值，也可以解析出JWT中的数据（只是某些编程API不支持），所以，不要在JWT中存放敏感数据！当使用编程API解析JWT出现签名错误时，如果已经解析出JWT中的数据，那么，这些数据也应该视为“不可被信任的数据”。

# 在项目中使用JWT

在项目中使用JWT需要：

- 当验证用户登录通过后，生成JWT，此JWT应该存入用户的ID、用户名或其它必要的非敏感数据同，此数据将响应到客户端
- 客户端在后续提交请求时，应该携带JWT，与此同时，服务器端应该解析此JWT，如果顺利解析通过，应该基于解析结果识别客户端的身份

则先将处理登录的方法的返回值由原本的`void`改为`String`。

在`UserServiceImpl`中的`login()`的最后位置，将原本“向`SecurityContext`中存入`Authentication`”的代码删除，并改为生成JWT，将JWT作为当前方法的返回值。

在`UserController`中处理登录请求时，如果登录功能，向客户端响应JWT。

关于客户端携带JWT，通常，业内惯用的做法是将JWT放在请求头中名为`Authorization`的属性中，服务器端通常使用过滤器（`Filter`）来接收并尝试解析JWT，之所以使用过滤器，主要原因有：

- 过滤器是Java EE中最早接收到请求的组件，并且，Spring Security在处理认证时，也是使用的过滤器，则处理JWT的过滤器必须先执行，将解析JWT得到的用户数据创建为`Authentication`并存入到`SecurityContext`中，然后，Spring Security的过滤器从`SecurityContext`才可以找到`Authentication`，运行在过滤器（Spring Security的）之前的只可能是过滤器（自己写的处理JWT的）
- 无论是哪种请求，都必须先经过过滤器，然后才可能被处理，所以，使用过滤器是一劳永逸的做法，不用纠结于处理哪个请求时才需要处理JWT

关于使用Knife4j调试时在请求头中携带JWT：

![image-20230905144156451](assets/image-20230905144156451.png)

为了确保自定义的过滤器先执行，再执行Spring Security处理认证的过滤器，需要在Spring Security的配置类中添加配置：

![image-20230905153526655](assets/image-20230905153526655.png)

另外，还需要注意：Spring Security默认都使用到了Session来处理`SecurityContext`，则如果成功通过认证，在接下来Session尚未过期的时间范围内，即便不携带JWT，Spring Security仍能够从Session中找到`SecurityContext`，进而找到`SecurityContext`中的`Authentication`以完成认证！

以上问题可能并不需要解决，如果要解决，可以在Spring Security的配置类中，将“Session管理的创建Session策略”配置为“无状态”，也就是不再使用Session：

![image-20230905155221764](assets/image-20230905155221764.png)

最后，关于解析JWT的过滤器，核心代码大致是：

```java
package cn.tedu.tmall.passport.filter;

import cn.tedu.tmall.common.pojo.authentication.CurrentPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.ArrayList;
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

        // 检查JWT的基本有效性，可自行再扩展其它规则
        if (!StringUtils.hasText(jwt)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 尝试解析JWT
        String secretKey = "8KS7u4F9ufaJa7sPD3fDa";
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);

        // 确定当事人
        CurrentPrincipal principal = new CurrentPrincipal();
        principal.setId(id);
        principal.setUsername(username);

        // 确定权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("临时的山寨权限"));

        // 将解析JWT得到的用户数据创建为Authorization并存入到SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, null, authorities);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // 放行，如果没有执行过滤器链的doFilter()则相当于“阻止”
        filterChain.doFilter(request, response);
    }

}
```

# 关于Redis

Redis是一款基于内存的、使用K-V结构存储数据的NoSQL非关系型数据库。

> 数据库：提供了数据读写操作的软件，通常，在没有特定的定义时，泛指关系型数据库。
>
> 关系型数据库：数据库中有“表”的概念，并且，各表中的数据可以存在关联，例如，在订单中存入用户的ID，则订单表与用户表就产生了关联
>
> 非关系型数据库：数据库中无法体现数据与数据之间的关系
>
> NoSQL：No SQL 或 No Operation SQL，通常出现在非关系型数据库中，表示在这些数据库中读写数据时不会涉及SQL语句
>
> 基于内存的：在使用Redis的过程中，无论是读数据，还是写数据，都是在内存中操作的。需要注意，Redis默认会自动将内存中的数据同步到硬盘上，即便计算机出现断电（包括重启），也会自动将硬盘上的数据同步回Redis对应的内存空间，对于用户而言，这些数据表现为“断电也不会丢失”

基于Redis的基本特征，所以：

- 用于提高查询效率：可以将MySQL中的数据（这些数据在硬盘上）读出来，并写入到Redis中，后续，当需要获取数据时，不再从MySQL中获取，而是改为从Redis中获取，由于Redis中的数据都是在内存中的，所以读取速度非常快
- 分担关系型数据库的压力，从而保护关系型数据库

从MySQL中读出来并写入到Redis中的数据，通常称之为“缓存”数据，一旦使用缓存数据，则MySQL和Redis中都存在相同的数据，但是，如果某次数据发生变化时，只修改了MySQL中的数据，却还没有修改Redis中的数据，则此时Redis中的数据是“不准确的”，这种两边数据不同的问题也称之为“数据一致性问题”。

关于数据一致性问题，针对不用的应用场景、不同的数据，可能需要“数据实时一致性”，或只需要保证“数据最终一致性”即可。

# Redis的数据类型

Redis中的经典数据类型有5种，分别是：`string`（一般值，例如：字符串、数值等直接字面能够表示的值） / `list`（有序列表） / `set`（无序的散列表） / `hash`（对应Java中的`Map`，通常用于存储需要修改属性值的对象） / `zset`（有序的散列表）

另外，还有：`bitmap` / `hyperloglog` / `stream` / `Geo`

# Redis的常用命令

- `redis-cli`：在终端容器中登录Redis控制台

  ![image-20230905172345666](assets/image-20230905172345666.png)

- `set KEY VALIE`：向Redis中存入`string`数据，如果使用的`KEY`尚不存在，则此命令会新增数据，如果`KEY`已经存在，则会修改数据，此前的数据将会被覆盖

  ![image-20230905172529933](assets/image-20230905172529933.png)

- `get KEY`：从Redis中取出`string`数据，如果`KEY`不存在，则返回`(nil)`，相当于Java中的`null`

  ![image-20230905172608133](assets/image-20230905172608133.png)

- `del KEY [KEY ...]`：删除Redis中的数据，将返回成功删除的数据的数量

  ![image-20230905172851029](assets/image-20230905172851029.png)

- `keys PATTERN`：根据模式（`PATTERN`）获取Key，如果Key存在，则返回，如果不存在，则返回`(empty list or set)`，也可以在模式中使用星号通配符

  ![image-20230905173249154](assets/image-20230905173249154.png)

  ![image-20230905173506382](assets/image-20230905173506382.png)

- `flushdb`：清空当前数据库

  ![image-20230905173608184](assets/image-20230905173608184.png)

更多命令可参考：https://www.cnblogs.com/antLaddie/p/15362191.html









