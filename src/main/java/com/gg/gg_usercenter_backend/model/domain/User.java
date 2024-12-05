package com.gg.gg_usercenter_backend.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 用户表
 * @Author GuiGuXiNan
 * @Date 2024/11/10 1:09
 * @Version 1.0
 **/
@Data
@TableName("`user`")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;

    /**
     * 昵称
     */
    private String username;

    /**
     * 登录账户
     */
    @TableField("userAccount")
    private String userAccount;

    /**
     * 用户头像
     */
    @TableField("avatarUrl")
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 0 - 正常
     */
    @TableField("userStatus")
    private Integer userStatus;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    @TableField("userRole")
    private Integer userRole;

    /**
     * 星球id
     */
    @TableField("planetId")
    private String planetId;

    public User(String userAccount, String newPassword, String planetId) {
        this.userAccount = userAccount;
        this.password = newPassword;
        this.planetId = planetId;
    }
    public User() {}
}