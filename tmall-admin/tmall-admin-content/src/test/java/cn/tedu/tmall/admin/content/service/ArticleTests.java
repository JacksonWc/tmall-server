package cn.tedu.tmall.admin.content.service;

import cn.tedu.tmall.admin.content.pojo.param.ArticleAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleTests {
    @Autowired
    IArticleService articleService;

    @Test
    void xxx(){
        ArticleAddNewParam articleAddNewParam=new ArticleAddNewParam();
        articleAddNewParam.setCategoryId((long)1);
        System.out.println(articleService.insertNewArticle(articleAddNewParam));
    }
}
