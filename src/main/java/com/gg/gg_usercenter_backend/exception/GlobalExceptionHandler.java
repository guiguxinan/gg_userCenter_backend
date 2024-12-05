package com.gg.gg_usercenter_backend.exception;

import com.gg.gg_usercenter_backend.common.BaseResponse;
import com.gg.gg_usercenter_backend.common.ErrorCode;
import com.gg.gg_usercenter_backend.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @ClassName GlobalExceptionHandler
 * @Author GuiGuXiNan
 * @Date 2024/11/26 19:54
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{
    private static final long serialVersionUID = -401441989740845440L;

    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> customExceptionHandler(CustomException e){
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse customExceptionHandler(RuntimeException e){
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
