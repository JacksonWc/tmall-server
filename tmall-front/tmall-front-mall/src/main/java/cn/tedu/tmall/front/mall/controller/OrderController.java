package cn.tedu.tmall.front.mall.controller;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import cn.tedu.tmall.front.mall.pojo.param.OrderAddNewParam;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;
import cn.tedu.tmall.front.mall.service.IOrderService;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 处理订单相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@Validated
@Api(tags = "6. 订单管理")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    public OrderController() {
        log.debug("创建控制器类对象：OrderController");
    }

    @PostMapping("/create")
    @ApiOperation("创建订单")
    @ApiOperationSupport(order = 100)
    public JsonResult add(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                          @Valid OrderAddNewParam orderAddNewParam) {
        log.debug("开始处理【创建订单】的请求，当事人：{}，参数：{}", currentPrincipal, orderAddNewParam);
        Long orderId = orderService.create(currentPrincipal, orderAddNewParam);
        return JsonResult.ok(orderId);
    }

    @GetMapping("/{id:[0-9]+}")
    @ApiOperation("根据ID查询订单")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                      @PathVariable @Range(min = 1, message = "请提交有效的订单ID值！") Long id) {
        log.debug("开始处理【根据ID查询订单】的请求，参数：{}", id);
        OrderStandardVO order = orderService.getStandardById(currentPrincipal, id);
        return JsonResult.ok(order);
    }

    @GetMapping("")
    @ApiOperation("查询订单列表")
    @ApiOperationSupport(order = 421)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "int")
    })
    public JsonResult listByCategory(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                     @Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【查询订单列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<OrderListItemVO> pageData = orderService.listByUser(currentPrincipal, pageNum);
        return JsonResult.ok(pageData);
    }

}