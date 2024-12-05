package com.gg.gg_usercenter_backend.exception;

import com.gg.gg_usercenter_backend.common.ErrorCode;
import lombok.Getter;

/**
 * @ClassName CustomException
 * @Author GuiGuXiNan
 * @Date 2024/11/26 17:45
 * @Version 1.0
 **/
@Getter
public class CustomException extends RuntimeException {
    /**
     * 异常码
     */
    private final int code;
    /**
     * 异常描述
     */
    private final String description;

    public CustomException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public CustomException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
}
