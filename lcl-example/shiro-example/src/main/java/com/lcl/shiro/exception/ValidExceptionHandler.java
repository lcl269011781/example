package com.lcl.shiro.exception;

import com.lcl.shiro.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ValidExceptionHandler
 * @Description: 参数校验异常捕获
 * @Author: Chunliang.Li
 * @Date: 2020/7/9 9:29
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@ControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResult validExcepiton(BindException e) {
        StringBuffer sb = new StringBuffer();
        e.getBindingResult().getFieldErrors().forEach(error -> sb.append(error.getDefaultMessage()).append(";"));
        return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, sb.toString(), null);
    }

}
