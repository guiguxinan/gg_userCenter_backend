package com.gg.gg_usercenter_backend.model.request;
import lombok.Data;
import java.io.Serializable;

/**
 * @ClassName 用户注册参数请求体
 * @Author GuiGuXiNan
 * @Date 2024/11/11 17:20
 * @Version 1.0
 **/
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 2904076819181468247L;
    /**
     * 账户
     */
    private String userAccount;
    /**
     * 密码
     */
    private String password;
    /**
     * 校验码
     */
    private String checkPassword;

    /**
     * 星球id
     */
    private String planetId;
}
