# 基于Spring Boot的Elasticsearch编程（续）

自定义的读写Elasticsearch数据的接口必须继承自`Repository`接口，通常，并不会直接继承它，而是改为其子级接口，以得到更多可用的方法，例如`CrudRepository`，或`ElasticsearchRepository`。

关于Spring Data Elasticsearch编程的官网文档：

> https://docs.spring.io/spring-data/elasticsearch/docs/4.2.1/reference/html/

在自定义的接口中，无论继承自`Repository`或其子级接口，都需要自行开发搜索功能，开发的方式有2种。

第1种是使用JPA风格定义抽象方法，可参考：

>  https://docs.spring.io/spring-data/elasticsearch/docs/4.2.1/reference/html/#elasticsearch.query-methods.criterions

例如：

```java
List<GoodsSearchPO> findByTitle(String x);
```

使用以上做法，不需要自行编写实现，也不需要其它配置，即可直接使用，非常简便，但是，不便于处理较为复杂的搜索（抽象方法的名称可能会比较复杂）！

第2种是完全自定义抽象方法（不需要使用JPA风格），然后，在方法上添加`@Query`注解来配置搜索的JSON字符串（搜索的JSON中的`query`的属性值），其中，参数名称使用`?`表示，如果有多个参数，使用`?`加上从0开始递增的序号来表示，例如第1个参数是`?0`，第2个参数是`?1`，以此类推！

例如：

```java
@Query("{\n" +
        "    \"bool\": {\n" +
        "      \"should\": [\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"title\": \"?0\"\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"brief\": \"?0\"\n" +
        "          }\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  }")
List<GoodsSearchPO> test(String keyword);
```

在搜索时，如果需要对结果进行分页，需要在抽象方法的参数列表中，添加最后一个参数，类型为`Pageable`，并且，除了执行分页以外，还应该获取分页的相关数据，应该使用`SearchPage`类型作为返回值类型，例如：

```java
@Query("{\n" +
        "    \"match\": {\n" +
        "      \"title\": \"?0\"\n" +
        "    }\n" +
        "  }")
SearchPage<GoodsSearchPO> testPage(String x, Pageable pageable);
```

> 提示：其实，只需要使用`Page`类型就可以获取一些分页参数，但是，不包含“搜索时命中多少条结果”，使用`SearchPage`类型就可以获取此数据。

后续，调用此方法时，通过此参数来传入分页的参数，`Pageable`是接口类型的，通常，可以使用`PageRequest.of(page, size)`来得到此类型的对象，此方法的第1个参数表示“页码”，取值为从0开始顺序编号的值，第2个参数表示“每页的数据量”，例如：

```java
@Test
void testPage() {
    String str = "绿茶";
    Pageable pageable = PageRequest.of(1, 3);
    SearchPage<GoodsSearchPO> searchPage = repository.testPage(str, pageable);
    SearchHits<GoodsSearchPO> searchHits = searchPage.getSearchHits();
    long totalHits = searchHits.getTotalHits();
    System.out.println("list.getContent() >>> " + searchPage.getContent());
    System.out.println("totalHits >>> " + totalHits);
    System.out.println("list.getTotalPages() >>> " + searchPage.getTotalPages());
    System.out.println("list.getNumber() >>> " + searchPage.getNumber());
    System.out.println("list.getSize() >>> " + searchPage.getSize());
}
```

如果需要将搜索结果中的字段进行高亮显示，则需要在抽象方法上添加`@HighLight`注解进行配置，例如：

```java
@Query("{\n" +
        "    \"bool\": {\n" +
        "      \"should\": [\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"categoryName\": \"?0\"\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"barCode\": \"?0\"\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"title\": \"?0\"\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"brief\": \"?0\"\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"match\": {\n" +
        "            \"keyword\": \"?0\"\n" +
        "          }\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  }")
@Highlight(fields = {@HighlightField(name = "title")},
    parameters = @HighlightParameters(preTags = "<font style='color: red;'>", postTags = "</font>"))
SearchPage<GoodsSearchPO> customSearch(String keyword, Pageable pageable);
```

测试效果如下：

```
@Test
void customSearch() {
    String str = "绿茶";
    Pageable pageable = PageRequest.of(0, 20);
    SearchPage<GoodsSearchPO> searchPage = repository.customSearch(str, pageable);
    SearchHits<GoodsSearchPO> searchHits = searchPage.getSearchHits();

    System.out.println("list.getTotalPages() >>> " + searchPage.getTotalPages());
    System.out.println("list.getNumber() >>> " + searchPage.getNumber());
    System.out.println("list.getSize() >>> " + searchPage.getSize());
    System.out.println("searchHits.getTotalHits() >>> " + searchHits.getTotalHits());

    for (SearchHit<GoodsSearchPO> searchHit : searchHits) {
        GoodsSearchPO goodsSearchPO = searchHit.getContent();
        List<String> highlightFields = searchHit.getHighlightField("title");
        if (highlightFields.size() > 0) {
            String s = highlightFields.get(0);
            goodsSearchPO.setTitle(s);
        }
        System.out.println(goodsSearchPO);
    }
}
```

# Spring AOP

AOP：面向切面的编程

注意：AOP并不是Spring独有的或原创的技术，只是Spring很好的支持了AOP！

AOP主要解决了横切关注的问题，例如：事务管理、安全管理、异常处理。

> 横切关注的问题：若干个不同的方法，都需要执行相同或高度相似的代码片段！

假设存在需求：统计项目中每个Service中的每个方法的执行耗时。

在Spring Boot项目中，处理AOP之前，需要添加依赖项：

```xml
<!-- Spring Boot支持AOP编程的依赖项 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
```





























