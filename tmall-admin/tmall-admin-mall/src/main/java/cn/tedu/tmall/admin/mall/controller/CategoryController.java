package cn.tedu.tmall.admin.mall.controller;

import cn.tedu.tmall.admin.mall.pojo.param.CategoryAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.service.ICategoryService;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "1. 类别管理")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @ApiOperation("新增类别")
    @ApiOperationSupport(order = 100)
    @PostMapping("add-new")
    @PreAuthorize("hasAuthority('/mall/category/simple')")
    public JsonResult addNewCategory(@Validated CategoryAddNewParam categoryAddNewParam) {
        categoryService.addNew(categoryAddNewParam);
        return JsonResult.ok();
    }

    @ApiOperation("根据父级查询子级列表")
    @ApiOperationSupport(order = 420)
    @PreAuthorize("hasAuthority('/mall/category/simple')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级类别ID", dataType = "long", required = true),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int")
    })


    @GetMapping("/list-by-parent")
    public JsonResult listByParent(Long parentId, Integer page) {
        Integer pageNum = page;
        if (page == null || page < 1) {
            pageNum = 1;
        }
        PageData<CategoryListItemVO> list
                = categoryService.listByParent(parentId, pageNum);
        return JsonResult.ok(list);
    }


    @PostMapping("/{id:[0-9]+}/delete")
    @ApiOperation("根据ID删除类别")
    @ApiOperationSupport(order = 200)
    @PreAuthorize("hasAuthority('/mall/category/delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult delete(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【根据ID删除类别】的请求，参数：{}", id);
        categoryService.delete(id);
        return JsonResult.ok();
    }

    @PostMapping("/{id:[0-9]+}/enable")
    @ApiOperation("启用类别")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult setEnable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【启用类别】的请求，参数：{}", id);
        categoryService.setEnable(id);
        return JsonResult.ok();
    }

    @PostMapping("/{id:[0-9]+}/disable")
    @ApiOperation("禁用类别")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult setDisable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【禁用类别】的请求，参数：{}", id);
        categoryService.setDisable(id);
        return JsonResult.ok();
    }

}
