package cn.tedu.tmall.admin.mall.handle;

import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.web.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
//        这是有对应构造方法的
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e) {
        //这是一次给客户端返回所有的错误
        // List<FieldError> fieldErrors = e.getFieldErrors();
        // for (FieldError fieldError : fieldErrors) {
        //     fieldError.getDefaultMessage(); // 拼接
        // }

        //这是一次给客户端返回一个错误，这种傻子只配这样的待遇
        String message = e.getFieldError().getDefaultMessage();
        return JsonResult.fail(ServiceCode.ERROR_BAD_REQUEST, message);
    }

    @ExceptionHandler
    public JsonResult handleAccessDeniedException(AccessDeniedException e) {
        String message = "操作失败，您当前登录的账号无此操作权限！";
        return JsonResult.fail(ServiceCode.ERROR_FORBIDDEN, message);
    }

    @ExceptionHandler
    public JsonResult doThrowable(Throwable t) {
        //比t.printStackTrace()--调用的system.out.println占用的资源稍多了;
        log.error("程序运行过程中出现了未处理的异常：" + t);
        String message = "服务器忙，请稍后再试！【开发阶段：同学们，" +
                "当你看到这句话时，你应该通过服务器端的控制台了解本次出现异常的原因，" +
                "并添加针对处理此异常的方法】";
        return JsonResult.fail(ServiceCode.ERROR_UNKNOWN, message);
    }
}
