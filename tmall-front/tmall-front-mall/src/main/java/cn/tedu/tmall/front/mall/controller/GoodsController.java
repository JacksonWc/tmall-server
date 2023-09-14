package cn.tedu.tmall.front.mall.controller;



import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.front.mall.service.IGoodsService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理商品相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("/goods")
@Validated
@Api(tags = "2. 商品管理")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    public GoodsController() {
        log.debug("创建控制器类对象：GoodsController");
    }

    @GetMapping("/{id:[0-9]+}")
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

    @GetMapping("/list-by-recommend")
    @ApiOperation("查询推荐的商品列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "int")
    })
    public JsonResult listByRecommend(@Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【查询推荐的商品列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<GoodsListItemVO> pageData = goodsService.listByRecommend(pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/list-by-category")
    @ApiOperation("根据类别查询商品列表")
    @ApiOperationSupport(order = 421)
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
