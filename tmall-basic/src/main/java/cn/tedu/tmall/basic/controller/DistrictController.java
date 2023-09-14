package cn.tedu.tmall.basic.controller;

import cn.tedu.tmall.basic.service.IDistrictService;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic/")
@Api(tags = "1. 字典数据-省市区")
public class DistrictController {
    @Autowired
    IDistrictService districtService;


    @ApiOperation("查询子集列表")
    @ApiOperationSupport(order = 450)
    @GetMapping("{parentId}/listByParentId")
    public JsonResult listByParentId(@PathVariable Long parentId){
        return JsonResult.ok(districtService.listByParentId(parentId));
    }


}
