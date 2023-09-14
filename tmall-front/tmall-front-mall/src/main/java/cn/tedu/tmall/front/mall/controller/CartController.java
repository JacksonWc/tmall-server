package cn.tedu.tmall.front.mall.controller;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.web.JsonResult;
import cn.tedu.tmall.front.mall.pojo.vo.CartListItemVO;
import cn.tedu.tmall.front.mall.service.ICartService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 处理购物车相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("/carts")
@Validated
@Api(tags = "5. 购物车管理")
public class CartController {

    @Autowired
    private ICartService cartService;

    public CartController() {
        log.debug("创建控制器类对象：CartController");
    }

    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    @ApiOperationSupport(order = 100)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "goodsNum", value = "商品数量", required = true, paramType = "query", dataType = "int")
    })
    public JsonResult add(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                          @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId,
                          @Range(min = 1, max = 100, message = "商品数量必须是1~100之间！") Integer goodsNum) {
        log.debug("开始处理【添加商品到购物车】的请求，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, goodsNum);
        cartService.add(currentPrincipal, goodsId, goodsNum);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车中的商品")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult delete(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                             @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId) {
        log.debug("开始处理【删除购物车中的商品】的请求，当事人：{}，商品：{}", currentPrincipal, goodsId);
        cartService.delete(currentPrincipal, goodsId);
        return JsonResult.ok();
    }

    @PostMapping("/goods-num/increase")
    @ApiOperation("将购物车中商品的数量增加1")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult increaseNum(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                  @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId) {
        log.debug("开始处理【将购物车中商品的数量增加1】的请求，当事人：{}，商品：{}", currentPrincipal, goodsId);
        Integer newNum = cartService.increaseNum(currentPrincipal, goodsId);
        return JsonResult.ok(newNum);
    }

    @PostMapping("/goods-num/increase-num")
    @ApiOperation("增加购物车中商品的数量")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "num", value = "商品数量", required = true, paramType = "query", dataType = "int")
    })
    public JsonResult increaseNum(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                  @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId,
                                  @Range(min = 1, max = 100, message = "商品数量必须是1~100之间！") Integer num) {
        log.debug("开始处理【增加购物车中商品的数量】的请求，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, num);
        Integer newNum = cartService.increaseNum(currentPrincipal, goodsId, num);
        return JsonResult.ok(newNum);
    }

    @PostMapping("/goods-num/reduce")
    @ApiOperation("将购物车中商品的数量减少1")
    @ApiOperationSupport(order = 320)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult reduceNum(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId) {
        log.debug("开始处理【将购物车中商品的数量减少1】的请求，当事人：{}，商品：{}", currentPrincipal, goodsId);
        Integer newNum = cartService.reduceNum(currentPrincipal, goodsId);
        return JsonResult.ok(newNum);
    }

    @PostMapping("/goods-num/reduce-num")
    @ApiOperation("减少购物车中商品的数量")
    @ApiOperationSupport(order = 321)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "num", value = "商品数量", required = true, paramType = "query", dataType = "int")
    })
    public JsonResult reduceNum(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                @Range(min = 1, message = "请提交合法的商品ID值！") Long goodsId,
                                @Range(min = 1, max = 100, message = "商品数量必须是1~100之间！") Integer num) {
        log.debug("开始处理【减少购物车中商品的数量】的请求，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, num);
        Integer newNum = cartService.reduceNum(currentPrincipal, goodsId, num);
        return JsonResult.ok(newNum);
    }

    @GetMapping("")
    @ApiOperation("查询购物车列表")
    @ApiOperationSupport(order = 400)
    public JsonResult aa(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal) {
        log.debug("开始处理【查询购物车列表】的请求，当事人：{}", currentPrincipal);
        List<CartListItemVO> list = cartService.list(currentPrincipal);
        return JsonResult.ok(list);
    }

}