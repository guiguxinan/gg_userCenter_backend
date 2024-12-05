package com.gg.gg_usercenter_backend.service;

import com.gg.gg_usercenter_backend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;

/**
* @author yeweiyuan
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-11-10 19:21:45
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账户
     * @param password 密码
     * @param checkPassword 校验码
     * @param planetId 星球id
     * @return 新用户 id
     */
    long userRegister(String userAccount, String password, String checkPassword, String planetId);

    /**
     * 用户登录
     * @param userAccount 账户
     * @param password 密码
     * @param httpServletRequest httpServletRequest
     * @return 用户
     */
    User userLogin(String userAccount, String password, HttpServletRequest httpServletRequest);

    /**
     * 退出登录
     * @param httpServletRequest
     * @return
     */
    Integer userLogout(HttpServletRequest httpServletRequest);


    /**
     * 用户脱敏
     * @param user 用户
     * @return 脱敏用户
     */
    User getSafetyUser(User user);
}
