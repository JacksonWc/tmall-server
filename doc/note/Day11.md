# 今日课程目标

* 掌握Redis常用基本数据类型指令应用
* 掌握Spring Boot项目中RedisTemplate对象的应用

# String 类型数据操作

## 概述
Redis中的一种最简单,最常用的数据类型,它存储的值可以是字符串,最大长度允许512M,
基于此类型可以实现博客中的点赞操作,分布式ID,日志的追加,基础对象的存储等.
## 常用指令操作
### set/get
```shell
127.0.0.1:6379> set n1 100
127.0.0.1:6379> get n1
127.0.0.1:6379> set n2 100 EX 10
127.0.0.1:6379> get n2
```
### incr/incrby
```shell
127.0.0.1:6379> incr n1  #步长默认为1
(integer)101
127.0.0.1:6379> incrby n1 10   #10为指定步长
(integer)111
```
### decr/decrby
```shell
127.0.0.1:6379> decr n1  #步长默认为1
(integer)110
127.0.0.1:6379> decrby n1 10   #10为指定步长
(integer)100
```
### append
```shell
127.0.0.1:6379> set name chengheng 
ok
127.0.0.1:6379> append name -guobin
(integer)16
127.0.0.1:6379> get name
chengheng-guobin
```
### strlen
```shell
127.0.0.1:6379> strlen name
(integer)16
```
### mset/mget
```shell
127.0.0.1:6379> mset a 100 b 200 c 300
OK
127.0.0.1:6379> mget a b c
1) "100"
2) "200"
3) "300"
```
### del
```shell
127.0.0.1:6379> del id name
(integer)2
```

# Hash数据类型操作

## 概述
这里hash数据类型指的是值的数据类型还是hash类型,底层存储类似HashMap，一般用于
存储一些完成的对象信息，例如购物车。

## 常用指令操作

### hset/hget
```shell
127.0.0.1:6379> hset user1 id 1
(integer) 1
127.0.0.1:6379> hset user1 name tony
(integer) 1
127.0.0.1:6379> hget user1 id
"1"
```
### hmset/hmget/hgetall
```shell
127.0.0.1:6379> hmset user2 id 2 name jack
ok
127.0.0.1:6379> hmget user2 id name
1)"2"
2)"jack"
127.0.0.1:6379> hgetall user2
1)"id"
2)"2"
3)"name"
4)"jack"
```
### hincrby

```shell
127.0.0.1:6379> hincrby user1 id 1
(integer) 11
127.0.0.1:6379> hincrby user1 id 1
(integer) 12
127.0.0.1:6379> hincrby user1 id -1
(integer) 11
```

### hexists
```shell
127.0.0.1:6379> hexists user1 id
(integer) 1

```
### hdel
```shell
127.0.0.1:6379> hdel user1 id name
(integer) 2
127.0.0.1:6379> hmget user1 id name
1) (nil)
2) (nil)
```

### hkeys/hvals

```shell
127.0.0.1:6379> hkeys user2
1) "id"
2) "name"
127.0.0.1:6379> hvals user2
1) "11"
2) "jack"
```

# List数据类型操作

## 概述
List数据类型类似Java中的LinkedList(底层实现是双向链表),
支持正向和反向查询，随机插入和删除效率比较高，基于此对象可以
实现热点数据存储，例如热点资讯、销量排名等。

## 常用指令

### lpush/lpop
向集合中head位置存储元素(下标为0)
```shell
127.0.0.1:6379> lpush lst1 1 2 3 4
(integer) 4
127.0.0.1:6379> lpop lst1
"4"
127.0.0.1:6379> lpop lst1
"3"

```
### rpush/rpop
```shell
127.0.0.1:6379> rpush lst2 a b c d
(integer) 4
127.0.0.1:6379> rpop lst2
"d"
127.0.0.1:6379> rpop lst2
"c"

```

```shell
127.0.0.1:6379> lpush lst3 10 20 30
(integer) 3
127.0.0.1:6379> rpop lst3
"10"
127.0.0.1:6379> rpop lst3
"20"

```

### llen
```shell
127.0.0.1:6379> lpush lst4 1 2 3 4 5
(integer) 5
127.0.0.1:6379> llen lst4
(integer) 5
```
### lindex
返回指定下标位置的元素

```shell
127.0.0.1:6379> lindex lst4 0
"5"
127.0.0.1:6379> lindex lst4 1
"4"
127.0.0.1:6379>

```

### linsert

```shell
127.0.0.1:6379> linsert lst4 before 5 6
(integer) 6
127.0.0.1:6379> lindex lst4 0
"6"
```
### lrange
```shell
127.0.0.1:6379> lpush lst5 1 2 3 4 5
(integer) 5
127.0.0.1:6379> lrange lst4 0 2
1) "5"
2) "4"
3) "3"
127.0.0.1:6379> lrange lst4 -5 -1  # 最右是-1
1) "5"
2) "4"
3) "3"
4) "2"
5) "1"
```
### lrem
```shell
127.0.0.1:6379> lpush lst6 1 2 3 3 4 5 5
(integer) 7
127.0.0.1:6379> lrem lst6 2 3
(integer) 2
```
### rpoplpush
从某个队列弹出一个元素然后放到另一个队列中。
```shell
127.0.0.1:6379> lpush q1 1 2 3
(integer) 3
127.0.0.1:6379> lpush q2 4 5 6
(integer) 3
127.0.0.1:6379> rpoplpush q1 q2
"1"
127.0.0.1:6379> lrange q2 0 4
1) "1"
2) "6"
3) "5"
4) "4"
127.0.0.1:6379>

```
### brpop/brpoplpush

brpop从容器中移除数据时,假如容器空了,则会等待(阻塞)

```shell
127.0.0.1:6379> lpush q3 1 2
(integer) 2
127.0.0.1:6379> brpop q3 10
(integer) 1
127.0.0.1:6379> brpop q3 10
(integer) 2
127.0.0.1:6379> brpop q3 10
阻塞

```

# Set数据类型

## 概述
Set类型类似Java中的HashSet对象,不允许元素重复并且无序.

## 常用指令操作

### sadd

```shell
127.0.0.1:6379> sadd s1  a a b b
(integer) 2

```

### smembers

```shell
127.0.0.1:6379> smembers s1
1)"a"
2)"b"

```

### spop

```shell
127.0.0.1:6379> spop s1
"b"
127.0.0.1:6379> spop s1
"a"
```
### scard
获取集合中元素的个数
```shell
127.0.0.1:6379> sadd s2 a b c d e
(integer) 5
127.0.0.1:6379> scard s2
(integer) 5
```

### srem
```shell
127.0.0.1:6379> srem s2 a
(integer) 1
```

### sunion

```shell
127.0.0.1:6379> sadd s3 a b
(integer) 2
127.0.0.1:6379> sadd s4 b c d
(integer) 3
127.0.0.1:6379> sunion s3 s4
1) "a"
2) "c"
3) "b"
4) "d"
```

### smove
将集合中的一个元素移动到另外一个集合.
```shell
127.0.0.1:6379> smove s3 s4 a
(integer) 1
127.0.0.1:6379> smembers s4
1) "a"
2) "c"
3) "b"
4) "d"
```

## 其它数据类型

基于增、删、改、查的逻辑自行去实践。


# RedisTemplate对象的应用

## 概述

RedisTemplate 是java封装了对Redis基本操作的一个对象，提供了很多模板方法。

## 入门案例

第一步：在项目中添加redis依赖
```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

```
第二步：在项目中配置redis连接属性(application.yml)
```yaml
spring:
   redis:
      host: localhost
      port: 6379
```
第三步：编写单元测试类，并添加相关测试方法

```java
package cn.tedu.tmall.admin.mall.redis;
@Setter
@Getter
class Point implements Serializable {
    private static final long serialVersionUID = 2737478896760951013L;
    private int x;
    private int y;
}

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void testGetRedisConnection(){
        RedisConnection connection =
                redisTemplate.getConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println("ping="+ping);
    }
    /**String类型数据操作*/
    @Test
    void testStringOper1(){
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("id",100);
        Object id = vo.get("id");
        System.out.println("id="+id);
        Long count = vo.increment("count", 1L);
        count=vo.increment("count", 1L);
        System.out.println("count="+count);

    }
    /**直接存储对象*/
    @Test
    void testStringOper2(){
        ValueOperations vo = redisTemplate.opsForValue();
        //.....
        Point p1=new Point();
        p1.setX(10);
        p1.setY(20);
        vo.set("p1",p1);
        Point p2=(Point)vo.get("p1");
        System.out.println(p1==p2);
        System.out.println(p1.equals(p2));
    }
    /**hash操作*/
    @Test
    void testHashOper01(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("cart:101","2001",3);
        hashOperations.put("cart:101","2002",5);
        Object o1 = hashOperations.get("cart:101", "2001");
        System.out.println(o1);
        Map entries = hashOperations.entries("cart:101");
        System.out.println(entries);
    }
}


```
## 定制RedisTemplate对象

RedisTemplate对象默认采用的是java中自带的序列化方式，假如我们希望对其序列化方式进行调整，通常会
在配置类中对此对象进行定制，例如：

```java
package cn.tedu.tmall.admin.mall.config;
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        //1.创建操作redis的java对象
        RedisTemplate<Object,Object> redisTemplate=
                new RedisTemplate<>();
        //2.设置连接工厂(访问redis也需要建立连接)
        redisTemplate.setConnectionFactory(connectionFactory);
        //3.设置RedisTemplate对象存取数据时的序列化和反序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }
}
```

# 总结(Summary)

## 重难点
* Redis常用数据类型在命令行的基本操作(应用场景、应用语法、操作实践)
* SpringBoot工程中RedisTemplate对象的应用。
## FAQ
* 常见数据类型应用场景及底层数据结构(例如跳表,SDS,quicklist)
* Redis配置文件名是什么？
* Redis支持远程连接Redis需要做哪些配置？(注释bind 127.0.0.1,protected-mode=no)
* RedisTemplate对象默认的序列化方式是什么？(JDK)
* ......
## Bug
* RedisTemplate不能直接对set存储数据进行increment操作。
* 无法连接远程redis
* ....




