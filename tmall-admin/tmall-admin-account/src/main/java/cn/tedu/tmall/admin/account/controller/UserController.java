package cn.tedu.tmall.admin.account.controller;


import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import cn.tedu.tmall.admin.account.service.IUserService;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/")
public class UserController {
    @Autowired
    IUserService userService;

    @ApiOperation("删除管理员的功能")
    @ApiOperationSupport(order = 110)
    @PostMapping("/{id}/deletemanager")
    public JsonResult deleteManager(@PathVariable Long id){
        userService.deleteManager(id);
       return JsonResult.ok();
    }


    @ApiOperation("获取管理员列表的功能")
    @ApiOperationSupport(order = 460)
    @GetMapping("listmanager")
    public JsonResult listManager(Integer page){
        Integer pageNum = page;
        if (page == null || page < 1) {
            pageNum = 1;
        }
        PageData<UserListItemVO> list
                = userService.listManager( pageNum);
        return JsonResult.ok(list);
    }


    @ApiOperation("启用和关闭账号的功能")
    @ApiOperationSupport(order = 300)
    //限制status只能传0，1
    @PostMapping("{status}/{id}/enableorclose")
    public  JsonResult enableOrClose(@PathVariable Integer status,@PathVariable Long id){
        userService.enableOrClose(status,id);
        return JsonResult.ok();
    }

}
