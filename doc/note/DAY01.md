# 1. 关于项目

本项目是一个通过茶文化相关资讯进行引流的，茶叶茶具相关的垂直电商平台。

在中国，喝茶的人很多，特别是近些年奶茶的流行，中国人对茶的需求明显提高，但是，绝大部分喝茶的人并不懂茶，甚至根本不了解茶，例如茶叶的种类、功效、喝茶的禁忌等，对茶叶的正常售价也不了解，更不了解茶文化，所以，我们搭建了这个平台，在平台上有许多关于茶相关的资讯，并对这些资讯进行了分类展示，希望用户可以更多的了解茶文化，同时，平台还有商城的功能，用户可以直接在我们的平台上购买茶相关的产品，比如茶叶、茶具等，避免用户因为不懂茶而踩坑。

项目中的主要功能包括：资讯相关的数据管理、商城相关的数据管理、用户相关的数据管理等。

# 2. 项目（预研）仓库

服务器端项目：https://gitee.com/chengheng2022/tmall-server-repo.git

客户端（管理员端）项目：https://gitee.com/chengheng2022/tmall-client-front-repo.git

客户端（普通用户端）项目：https://gitee.com/chengheng2022/tmall-client-admin-repo.git

# 3. 项目（授课）仓库

服务器端项目：https://gitee.com/chengheng2022/jsd2305-tmall-server-teacher.git

# 4. 数据库与数据表设计规范

关于各数据表的ID，只要是自动编号的，都应该使用`BIGINT`类型，哪怕是这张表的数据绝对不可能超过`INT`的上限！目的是为了“统一”！

表必备三字段：`id`, `gmt_create`, `gmt_modified`。 

- 说明：其中`id`必为主键，类型为`unsigned bigint`、单表时自增、步长为 1。`gmt_create`, `gmt_modified`的类型均为`date_time`类型，前者现在时表示主动创建，后者过去分词表示被 动更新。

关于`unsigned`，是用于整型类型的字段的，表示“无符号位”，只要字段的值不可能为负数，就应该将字段设置为`unsigned`，目的是为了避免写入错误的负数值，并清晰的表现设计意图。

字段允许适当冗余，以提高查询性能，但必须考虑数据一致。冗余字段应遵循： 

- 不是频繁修改的字段。
- 不是`varchar`超长字段，更不能是`text`字段。 
- 正例：商品类目名称使用频率高，字段长度短，名称基本一成不变，可在相关联的表中冗余存 储类目名称，避免关联查询。

小数类型为`decimal`，禁止使用`float`和`double`。 

- 说明：`float`和`double`在存储的时候，存在精度损失的问题，很可能在值的比较时，得到不 正确的结果。如果存储的数据范围超过`decimal`的范围，建议将数据拆成整数和小数分开存储。

在设计普通用户能够看到数据列表的类型时，极大概率需要设计“排序序号”字段，通常会根据此字段降序排列。

`varchar`是可变长字符串，不预先分配存储空间，长度不要超过`5000`，如果存储长度大于此值，定义字段类型为`text`，独立出来一张表，用主键来对应，避免影响其它字段索引效率。

# 5. 商城管理相关的数据表

- 类别
- 商品
- 商品详情
- 评论
- 审核日志
- 收货地址
- 订单
- 订单中的商品

# 6. 设计项目界面的原型

（略过）

注意：需要结合原型再次检查数据表的设计是否合理！

# 7. 技术选型

前端：客户端

后端：服务器端

前台：普通用户使用的

后台：管理团队使用的

前端（网页端）技术：VUE脚手架 + Element UI + Axios等

后端（服务器端）技术：Maven + Spring Boot + Spring MVC + MyBatis Plus + Spring Security + Knife4j + Spring Validation + ???

> 问：为什么要使用Spring Boot？
>
> 答：Spring Boot能够快速搭建项目，其主要特征有：依赖管理、自动配置。关于依赖管理：你只需要添加组合的`starter`依赖项，就可以将相关的协调的依赖项都添加到你的项目中，例如，添加`spring-boot-starter-web`后，Spring MVC、Tomcat等相关的依赖项都被包含在其中；关于自动配置：Spring Boot完成了许多低级的大众化配置，基于“约定大于配置”的思想，实现“开箱即用”的效果。

# 8. 项目结构

建议使用聚合的Maven项目（项目中包含子级模块项目），主要因为：

- 多个子模块项目需要使用相同的依赖项，包括各依赖项的版本也应该是相同的
- 多个子模块项目需要使用到相同的类

此项目的结构大致是：

```
tmall-server
-- tmall-common：公共模块，被其它模块所依赖
-- tmall-basic：基础数据模块，主要提供省市区数据的访问
-- tmall-attachment：附件数据模块，主要提供文件上传与浏览的服务
-- tmall-passport：单点登录模块，主要提供用户登录、退出登录及相关的服务
-- tmall-admin：后台管理模块，此模块不实现具体服务
-- -- tmall-admin-account：账号后台管理模块
-- -- tmall-admin-content：资讯后台管理模块
-- -- tmall-admin-mall：商城后台管理模块
-- tmall-front：前台管理模块，此模块不实现具体服务
-- -- tmall-front-account：账号前台管理模块
-- -- tmall-front-content：资讯前台管理模块
-- -- tmall-front-mall：商城前台管理模块
```

关于父级项目的创建参数：

- Name：`jsd2305-tmall-server-teacher` / `tmall-server`
- Group：`cn.tedu`

- Artifact：`tmall-server`
- Package Name：不重要
- Java：8
- Spring Boot版本：不重要，在创建过程中随意勾选2.x的版本，当创建完成后，在`pom.xml`中修改为`2.5.x`的版本
- 勾选依赖：无



