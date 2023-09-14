package cn.tedu.tmall.admin.content.controller;

import cn.tedu.tmall.admin.content.pojo.param.ArticleAddNewParam;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.admin.content.service.IArticleService;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "2.文章管理")
@RestController
@RequestMapping("/content/articles")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @ApiOperation("新增文章")
    @ApiOperationSupport(order = 1)
    @PostMapping("add-new")
    public JsonResult insertNewArticle(ArticleAddNewParam articleAddNewParam){
        articleService.insertNewArticle(articleAddNewParam);
        return JsonResult.ok();
    }

    @ApiOperation("文章列表的分页展示")
    @ApiOperationSupport(order = 460)
    @GetMapping("/{pageNum}/listallarticles")
    public JsonResult listAllArticles(@PathVariable Integer pageNum){
        PageData<ArticleListItemVO> pageData=articleService.listAllArticles(pageNum);
        return JsonResult.ok(pageData);
    }
}
