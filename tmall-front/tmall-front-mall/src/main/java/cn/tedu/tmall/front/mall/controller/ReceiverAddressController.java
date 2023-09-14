package cn.tedu.tmall.front.mall.controller;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.web.JsonResult;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressAddNewParam;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressUpdateParam;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import cn.tedu.tmall.front.mall.service.IReceiverAddressService;
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
import java.util.List;

/**
 * 处理收货地址相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@RestController
@RequestMapping("/receiver-addresses")
@Validated
@Api(tags = "4. 收货地址管理")
public class ReceiverAddressController {

    @Autowired
    private IReceiverAddressService receiverAddressService;

    public ReceiverAddressController() {
        log.debug("创建控制器类对象：ReceiverAddressController");
    }

    @PostMapping("/add-new")
    @ApiOperation("添加收货地址")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                             @Valid ReceiverAddressAddNewParam receiverAddressAddNewParam) {
        log.debug("开始处理【添加收货地址】的请求，当事人：{}，参数：{}", currentPrincipal, receiverAddressAddNewParam);
        receiverAddressService.addNew(currentPrincipal, receiverAddressAddNewParam);
        return JsonResult.ok();
    }

    @PostMapping("/{id:[0-9]+}/delete")
    @ApiOperation("删除收货地址")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址ID", required = true, dataType = "long")
    })
    public JsonResult delete(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                             @PathVariable @Range(min = 1, message = "请提交有效的收货地址ID值！") Long id) {
        log.debug("开始处理【删除收货地址】的请求，当事人：{}，参数：{}", currentPrincipal, id);
        receiverAddressService.delete(currentPrincipal, id);
        return JsonResult.ok();
    }

    @PostMapping("/{id:[0-9]+}/update")
    @ApiOperation("修改收货地址")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址ID", required = true, dataType = "long")
    })
    public JsonResult update(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                             @PathVariable @Range(min = 1, message = "请提交有效的收货地址ID值！") Long id,
                             @Valid ReceiverAddressUpdateParam receiverAddressUpdateParam) {
        log.debug("开始处理【修改收货地址】的请求，当事人：{}，参数：{}", currentPrincipal, receiverAddressUpdateParam);
        receiverAddressService.updateInfoById(currentPrincipal, id, receiverAddressUpdateParam);
        return JsonResult.ok();
    }

    @PostMapping("/{id:[0-9]+}/set-default")
    @ApiOperation("设置默认收货地址")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址ID", required = true, dataType = "long")
    })
    public JsonResult setDefault(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                 @PathVariable @Range(min = 1, message = "请提交有效的收货地址ID值！") Long id) {
        log.debug("开始处理【设置默认收货地址】的请求，当事人：{}，参数：{}", currentPrincipal, id);
        receiverAddressService.setDefault(currentPrincipal, id);
        return JsonResult.ok();
    }

    @GetMapping("/{id:[0-9]+}")
    @ApiOperation("根据ID查询收货地址")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal,
                                      @PathVariable @Range(min = 1, message = "请提交有效的收货地址ID值！") Long id) {
        log.debug("开始处理【根据ID查询收货地址】的请求，当事人：{}，参数：{}", currentPrincipal, id);
        ReceiverAddressStandardVO queryResult = receiverAddressService.getStandardById(currentPrincipal, id);
        return JsonResult.ok(queryResult);
    }

    @GetMapping("")
    @ApiOperation("查询收货地址列表")
    @ApiOperationSupport(order = 421)
    public JsonResult listByUser(@AuthenticationPrincipal @ApiIgnore CurrentPrincipal currentPrincipal) {
        log.debug("开始处理【查询收货地址列表】的请求，当事人：{}", currentPrincipal);
        List<ReceiverAddressListItemVO> list = receiverAddressService.listByUser(currentPrincipal);
        return JsonResult.ok(list);
    }

}
