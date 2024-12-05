package com.gg.gg_usercenter_backend.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName 用户注册参数请求体
 * @Author GuiGuXiNan
 * @Date 2024/11/11 17:20
 * @Version 1.0
 **/
@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2904076819181468247L;
    /**
     * 账户
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;

}
