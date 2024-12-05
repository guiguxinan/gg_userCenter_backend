package com.gg.gg_usercenter_backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName 通用返回类
 * @Author GuiGuXiNan
 * @Date 2024/11/26 16:30
 * @Version 1.0
 **/
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private String description;

    public BaseResponse(int code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }
    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, T data, String description) {
        this.code = code;
        this.data = data;
        this.message = "";
        this.description = description;
    }
    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = "";
        this.description = "";
    }
    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
        this.data = null;
    }

    public BaseResponse(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
