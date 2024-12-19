package com.gg.gg_usercenter_backend.common;
import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {
    SUCCESS(0,"success",""),
    PARAM_NULL(40000,"请求参数错误",""),
    NULL_PARAM_ERROR(40001,"请求参数为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","")
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误信息
     */
    private final String message;
    /**
     * 错误描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
