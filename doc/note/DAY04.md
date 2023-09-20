# 19. 新增类别 -- Controller

关于Controller层，它应该解决一些问题，包括：

- 接收请求，通过Spring MVC框架实现
- 响应结果，通过Spring MVC框架实现
- 处理异常（业务规则错误，请求参数错误），通过Spring MVC框架实现
- 检查请求参数的基本有效性，通过Spring Validation框架实现
- 提供API文档，通过Knife4j框架实现

则需要添加相关依赖：

```xml
<knife4j-spring-boot.version>2.0.9</knife4j-spring-boot.version>
```

```xml
<!-- Spring Boot支持Spring MVC的依赖项 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
<!-- Spring Boot支持Spring Validation的依赖项，用于检查参数的基本有效性 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
<!-- Knife4j Spring Boot：在线API文档 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j-spring-boot.version}</version>
</dependency>
```

具体的编码步骤（部分操作不区分先后顺序）：

- 添加Knife4j的配置类

  - 复制此配置类文件即可，不提倡自己写
  - 注意检查配置类中配置的Controller根包是否正确
  - 可能需要删除`target`文件夹

- 在配置文件（`application.yaml`）中，启用Knife4j的增强模式

  - ```yaml
    knife4j:
      enable: true
    ```

- 在配置文件（`application.yaml`）中，配置JSON结果中不显示为`null`的属性

  - ```yaml
    spring:
      jackson:
        default-property-inclusion: non_null
    ```

- 配置API文档：

  - 类上使用`@Api`的`tags`属性，建议在属性值上添加数字序号
  - 方法上使用`@ApiOperation`配置名称，使用`@ApiOperationSupport`的`order`属性配置显示顺序
  - 参数类的属性上使用`@ApiModelProperty`注解，通过`value`配置名称，通过`required`配置是否必须提交此参数，通过`example`配置示例值
    - 注意：`required`属性只具有显示效果和在API文档的调整功能中的检查效果，除了API文档的调试以外发起的请求，`required`属性的配置无法确保请求中包含此参数

- 添加Validation的配置类，启用快速失败
  - 建议启用快速失败
- 自定义全局异常处理器
  - 除了已经明确的异常以外，还应该添加处理`Throwable`的方法
    - 不要使用`e.printStackTrace()`来观察异常的跟踪信息，建议使用日志

- 自定义控制器类，在类中添加处理请求的方法
  - 如果某个请求“以获取数据为主要目的”，则使用`GET`请求，否则，使用`POST`请求

# 20. 基于Spring JDBC的事务管理

事务（Trancation）：是关系型数据库（例如：MySQL / MariaDB / SQL Server等）一种能够使得多个写操作（增 / 删 / 改）要么全部成功，要么全部失败的机制。

假设存在需求：小奇同学向小斌同学转账5000元，则需要：

```mysql
update 账户表 set 余额=余额-5000 where 账户='小奇';
```

```mysql
update 账户表 set 余额=余额+5000 where 账户='小斌';
```

在基于Spring JDBC的项目中，当需要保证某个方法是事务性的，只需要采取“声明式事务”的做法，即：添加`@Transactional`注解即可！

关于添加`@Transactional`注解：

- 添加在业务实现类中的方法上
  - 仅作用于当前方法
  - 仅能作用于重写的方法
- 添加在业务实现类上
  - 作用于当前类中所有重写的方法
- 添加在业务接口中的抽象方法上
  - 仅作用于实现类中重写的此方法
- 【建议】添加在业务接口上
  - 作用于实现类中所有重写的方法

提示：Spring JDBC是通过接口代理的方式实现事务管理的，所以，只能作用于接口中声明的方法，如果实现类有自定义的方法，`@Transactional`对这些自定义方法是无效的！

关于建议在接口上添加`@Transactional`注解：从学术方面讨论，应该按需在方法添加注解！如果只是在方法上添加注解，某些业务可能原本并不需要是事务性，但由于项目升级、需求变化，会被调整为“需要事务性的”，可能会忘记补充添加`@Transactional`注解，则存在安全隐患！在接口上添加注解，会使得所有方法都是事务性的，包括那些不需要事务性保障的方法，则存在一定的浪费，但是，对性能的影响并不明显！

在数据库中，当需要操作事务时，大致的过程是：

```
开启事务：BEGIN
执行你的业务

如果成功，则提交事务：COMMIT
如果失败，则回滚事务：ROLLBACK
```

在基于Spring JDBC的事务管理中，大致的过程是：

```
开启事务：BEGIN
try {
	执行你的业务
	提交事务：COMMIT
} catch (RuntimeException e) {
	回滚事务：ROLLBACK
}
```

可以看到，Spring JDBC默认会根据`RuntimeException`回滚事务，如果需要修改此规则，可以配置`@Transactional`的`rollbackFor`属性或`rollbackForClassName`属性，例如：

```java
@Transactional(rollbackFor = {NullPointerException.class,
    						ArrayIndexOutOfBoundsException.class})
```

```java
@Transactional(rollbackForClassName = {"java.lang.NullPointerException",
    						"java.lang.ArrayIndexOutOfBoundsException"})
```

而且，还有`noRollbackFor`和`noRollbackForClassName`属性，用于指定不会发生回滚的异常类型。

关于事务，你还应该了解或记忆：

- 事务的ACID特性（原子性、一致性、隔离性、持久性）
- 事务的传播
- 事务的隔离级别

###### 

# 思考题

## GET与POST有什么区别

- GET请求中的参数会通过问号（`?`）拼接在URL中，所以，不适合传输敏感数据，也不可以传输大量数据，而POST请求中的参数会放在请求体中，可以用于传输敏感数据，也可以传输大量数据
- GET的优点在于比POST略快，并且，适用于保存（收藏）、分享等应用场景
- 在开发实践中，以获取数据为主要目的的请求使用GET，其它使用POST

## 在项目中为什么要处理异常

- 如果没有处理异常，错误将会向客户端响应500，用户无法根据这样的错误了解具体的原因，也就无法改正自己的问题，将无法提交正确的请求及请求参数。

## 使用Knife4j主要分几步

- 添加依赖
- 添加配置类
- 在配置文件中添加“启用Knife4j的增强模式”配置
- 其它细节：使用相关注解配置API文档的显示

## 如何使用Validation检查自定义的（自行封装的）请求参数

- 添加依赖
- 通过配置类配置快速失败
  - 非必要
- 在处理请求的方法中，在参数上添加`@Valid`或`@Validated`注解，标记此参数需要被检查
- 在自定义参数类的属性上，添加检查注解，以配置检查规则及检查不通过时的提示文本



