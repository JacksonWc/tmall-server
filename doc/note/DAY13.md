# 在其它项目中实现单点登录的认证与授权

当前，用户在`tmall-passport`登录后，将得到JWT，在其它项目中，当需要实现认证与授权时，只需要具备解析此JWT的功能，并将解析结果创建为`Authentication`并存入到`SecurityContext`中即可，后续的认证与授权仍由Spring Security框架自动处理。

要在其它项目中实现单点登录的认证与授权，需要将`tmall-passort`中的相关代码复制到其它项目中：

- 添加依赖：`hutool` / `spring-boot-starter-data-redis` / `fastjson` / `jjwt` / `spring-boot-starter-security`
- `IUserCacheRepository`
- `UserCacheRepository`
- `JwtAuthorizationFilter`
- `SecurityConfiguration`
  - 可以删除`PasswordEncoder`的`@Bean`方法
  - 对“白名单”进行必要的微调
- 复制配置文件中关于JWT的自定义配置
- 全局异常处理器中“权限不足”的处理

# 关于`@RequestBody`注解

在处理请求的方法的参数上，关于`@RequestBody`注解：

- 如果添加此注解，则客户端提交的请求参数必须是对象格式的
  - 在此情况下，Knife4j的调试界面不会提供各请求参数的输入框，而是自行提交整个JSON格式的对象数据
- 如果没有添加此注解，则客户端提交的请求参数必须是FormData格式的
  - 在此情况下，Knife4j的调试界面会提供各请求参数的输入框









