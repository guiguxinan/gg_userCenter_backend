package com.gg.gg_usercenter_backend.common;

/**
 * 返回工具类
 * @ClassName ResultUtils
 * @Author GuiGuXiNan
 * @Date 2024/11/26 16:38
 * @Version 1.0
 **/
public class ResultUtils {
    /**
     * 返回成功
     * @param data 前端传入数据
     * @return 返回通用返回类对象
     * @param <T> 返回类型
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "success", data);
    }
    public static <T> BaseResponse<T> success(T data, String description) {
        return new BaseResponse<>(0, "success", data, description);
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }


    public static BaseResponse<?> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, null, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), message,null, description);
    }
}
