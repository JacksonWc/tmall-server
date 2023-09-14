package cn.tedu.tmall.admin.mall.controller;

import cn.tedu.tmall.admin.mall.pojo.param.GoodsAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.admin.mall.service.IGoodsService;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "2.商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IGoodsService goodsService;


    @ApiOperation("发布商品")
    @PreAuthorize("hasAuthority('/mall/goods/simple')")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult insertNewGoods(GoodsAddNewParam goodsAddNewParam, @AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal, @ApiIgnore HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        goodsService.getNewGoods(currentPrincipal, remoteAddr, goodsAddNewParam);
        return JsonResult.ok();
    }

    @ApiOperation("商品列表的分页展示")
    @PreAuthorize("hasAuthority('/mall/goods/simple')")
    @ApiOperationSupport(order = 400)
    @GetMapping("/{pageNum}/listallgoods")
    public JsonResult listAllGoods(@PathVariable Integer pageNum) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<GoodsListItemVO> list
                = goodsService.lisAllGoods(pageNum);
        return JsonResult.ok(list);
    }

    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/mall/goods/simple')")
    @ApiOperation("根据ID查询商品")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@PathVariable @Range(min = 1, message = "请提交有效的商品ID值！") Long id) {
        log.debug("开始处理【根据ID查询商品】的请求，参数：{}", id);
        GoodsStandardVO queryResult = goodsService.getStandardById(id);
        return JsonResult.ok(queryResult);
    }


    @GetMapping("/list-by-category")
    @PreAuthorize("hasAuthority('/mall/goods/simple')")
    @ApiOperation("根据类别查询商品列表")
    @ApiOperationSupport(order = 421)
    //写了这个（不写那就是根据实际方法有参数就行）就是文档说明那里会更准确，不是说一定要从抽象路径中获取数据的
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "商品类别ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "int")
    })
    public JsonResult listByCategory(@Range(message = "请提交有效的商品类别ID值！") Long categoryId,
                                     @Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【根据类别查询商品列表】的请求，父级商品：{}，页码：{}", categoryId, page);
        Integer pageNum = page == null ? 1 : page;
        PageData<GoodsListItemVO> pageData = goodsService.listByCategory(categoryId, pageNum);
        return JsonResult.ok(pageData);
    }

}
