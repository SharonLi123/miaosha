package top.safun.miaosha.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.safun.miaosha.result.CodeMessage;
import top.safun.miaosha.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//该拦截器用于拦截异常
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException globalException=(GlobalException)e;
            return Result.error(globalException.getCodeMessage());
        } else if (e instanceof BindException){
            BindException bindException=(BindException)e;
            List<ObjectError> objectErrors=bindException.getAllErrors();
            ObjectError error=objectErrors.get(0);
            String msg=error.getDefaultMessage();
            return Result.error(CodeMessage.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMessage.SERVER_ERROR);
        }

    }
}
