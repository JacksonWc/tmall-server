# 基于RBAC的用户与权限的设计

**什么是RBAC？**

**RBAC** = **R**ole-**B**ased **A**ccess **C**ontrol：基于角色的访问控制

**如何设计用户及密切相关的数据？**

在每个系统中，“用户”必须有的属性至少包括：

- 登录的唯一标识（通常是用户名，也可以是邮箱、手机号码）
- 登录的凭证（通常是密码）
- 出于管理需求，通常应该包括一些“状态”的属性，例如“是否启用”，因为用户数据一般是不允许被删除的，另外，状态数据可以按需设计，可以有多项
- 按照软件的需求，设计其它属性，例如：性别、生日、学历、收入水平
- 权限

**如何设计用户的权限？**

- 【简单的设计】在用户表中添加字段，表示用户的身份，例如添加“是否为管理员”字段，如果值为0，视为普通用户，如果值为1，视为管理员，或允许其它值对应其它的身份。这种做法的缺点在于无法细分各管理员的权限，即所有管理员的权限是完全相同的！

- 【较好的设计】除了用户表以外，还专门设计权限表，在权限表中列举一些权限的“标识”，例如，使用“添加账号”字样的字符串表示“添加账号”的权限，使用“删除账号”字样的字符串表示“删除账号”的权限，后续，在程序中，将通过分支语句判断用户的权限中是否包含“添加账号”或“删除账号”等字符串值，以判断用户是否具有某种权限，最终，还会添加一张用户与权限的关联表，此表中会记录用户与权限的关联关系。

  | id   | username   | password |
  | ---- | ---------- | -------- |
  | 1    | wangkejing | 123456   |
  | 2    | fanchuanqi | 987654   |

  | id   | authority |
  | ---- | --------- |
  | 1    | 添加账号  |
  | 2    | 删除账号  |

  | id   | user_id | authority_id |
  | ---- | ------- | ------------ |
  | 1    | 1       | 1            |
  | 2    | 2       | 1            |
  | 3    | 2       | 2            |

  这种做法的缺点在于：当系统中的用户数据较多时，如果很多用户的权限是相同的，管理这些权限相对比较麻烦，比如，为这些用户全部添加某种权限，或为这些用户全部删除某种权限。

- 【推荐的做法：RBAC】使用5张数据表：用户表、角色表、权限表、用户与角色的关联表、角色与权限的关联表

  | id   | username   | password |
  | ---- | ---------- | -------- |
  | 1    | wangkejing | 123456   |
  | 2    | fanchuanqi | 987654   |
  | 3    | liuguobin  | 456321   |

  | id   | role_name  |
  | ---- | ---------- |
  | 1    | 系统管理员 |
  | 2    | 普通用户   |

  | id   | user_id | role_id |
  | ---- | ------- | ------- |
  | 1    | 1       | 1       |
  | 2    | 1       | 2       |
  | 3    | 2       | 2       |
  | 4    | 3       | 2       |

  | id   | authority    |
  | ---- | ------------ |
  | 1    | 进入后台管理 |
  | 2    | 查看用户列表 |
  | 3    | 查看商品列表 |
  | 4    | 发表评论     |

  | id   | role_id | authority_id |
  | ---- | ------- | ------------ |
  | 1    | 1       | 1            |
  | 2    | 1       | 2            |
  | 3    | 2       | 3            |
  | 4    | 2       | 4            |

# 验证用户登录

创建`tmall-passport`项目，此项目是“单点登录”服务模块，主要实现：

- 用户登录
- 退出登录
- 查看登录日志

在不使用Spring Security框架的情况下，也可以自行实现登录，按照相对固定的开发流程进行处理即可，也就是：DAO > Service > Controller，所以，关于验证登录，当前的开发顺序应该是：

- Mapper

  - 创建（复制）`User`实体类

  - 创建`UserLoginInfoVO`类，此类中的属性：`id / username / password / enable / 其它`（暂不处理权限相关）

  - 创建`UserMapper`接口，继承自`BaseMapper`接口，并在接口中添加抽象方法：

    ```java
    UserLoginInfoVO getLoginInfoByUsername(String username);
    ```

  - 在`src/main/resources`下创建`mappers`文件夹（在配置文件中配置的位置），并在此文件夹下添加`UserMapper.xml`，在此XML中配置以上抽象方法对应的SQL语句

  - 在`src/test/java`下的根包下，创建必要的子包，然后创建测试类，在测试类中编写测试方法，以检验以上功能是否可以正确执行

- Repository

  - 创建`IUserRepository`接口，声明与Mapper层同样的抽象方法
  - 创建`UserRepositoryImpl`类，实现以上接口，调用Mapper对象实现方法

- Service

  - 创建`UserLoginInfoParam`类，此类中的属性：`username / password`

  - 创建`IUserService`接口，并在接口中添加抽象方法（暂不关心返回值类型）

    ```java
    void login(UserLoginInfoParam userLoginInfoParam);
    ```

  - 创建`UserServiceImpl`类，在类中实现以上抽象方法，大致是：

    ```java
    public void login(UserLoginInfoParam userLoginInfoParam) {
        // 从参数中取出username
        // 调用repository，根据用户名查询用户数据
        // 判断查询结果是否为null
        // 是：抛出异常（用户名不存在）
        
        // 判断查询结果中的账号状态（enable）是否“不处于”正常状态
        // 是：抛出异常（账号状态异常）
        
        // 【注意】由于暂时还没有引入Security框架，则暂时无法处理BCrypt密文，需要暂时将数据表中各账号的密码暂时改为明文密码值
        // 判断查询结果中的密码与参数中的密码是否“不匹配”
        // 是：抛出异常（密码错误）
        
        // TODO 处理登录日志相关
    }
    ```

  - 完成后，自行编写测试

- Controller

  - 创建`UserController`，参考此前其它Controller进行处理即可
  - 完成后，通常API文档的调试功能进行测试访问