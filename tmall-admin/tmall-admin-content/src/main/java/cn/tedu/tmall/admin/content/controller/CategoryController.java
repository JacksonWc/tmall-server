package cn.tedu.tmall.admin.content.controller;


import cn.tedu.tmall.admin.content.dao.cache.ICategoryCacheRepository;
import cn.tedu.tmall.admin.content.pojo.param.CategoryAddNewParam;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.content.service.ICategoryService;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "1.资讯的分类管理")
@RestController
@RequestMapping("/content/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICategoryCacheRepository categoryCacheRepository;

    @ApiOperation("新增资讯的分类")
    @ApiOperationSupport(order = 1)
    @PostMapping("add-new")
    public JsonResult insertNewCategory(@Validated CategoryAddNewParam categoryAddNewParam){
        categoryService.insertNewCategory(categoryAddNewParam);
       return JsonResult.ok();
    }

    @GetMapping("list")
    @ApiOperation("从redis中读取分类列表")
    @ApiOperationSupport(order = 450)
    public JsonResult list(){
      List<CategoryListItemVO>  categoryListItemVOS=categoryCacheRepository.listAll();
      return  JsonResult.ok(categoryListItemVOS);
    }

}
