package com.gg.gg_usercenter_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.gg_usercenter_backend.common.ErrorCode;
import com.gg.gg_usercenter_backend.exception.CustomException;
import com.gg.gg_usercenter_backend.model.domain.User;
import com.gg.gg_usercenter_backend.service.UserService;
import com.gg.gg_usercenter_backend.mapper.UserMapper;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.gg.gg_usercenter_backend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author yeweiyuan
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-11-10 19:21:45
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    /**
     *  盐值：混淆密码
     */
    private static final String SALT = "yyw";

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param userAccount 账户
     * @param password 密码
     * @param checkPassword 校验码
     * @param planetId 星球id
     * @return 新用户 id
     */
    @Override
    public long userRegister(String userAccount, String password, String checkPassword, String planetId) {
        // 1. 校验
        if (StringUtils.isAnyEmpty(userAccount, password, checkPassword, planetId)) {
            throw new CustomException(ErrorCode.PARAM_NULL,"参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new CustomException(ErrorCode.PARAM_NULL, "用户账号长度不能小于4位");
        }
        if (password.length() < 8){
            throw new CustomException(ErrorCode.PARAM_NULL, "用户密码不能小于8位");
        }
        if (planetId.length() > 5){
            throw new CustomException(ErrorCode.PARAM_NULL, "星球编号不能大于5位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?？~！@#￥%……&*（）——+|{}【】‘；：“”’。，·《》]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new CustomException(ErrorCode.PARAM_NULL, "账号只支持数字和英文");
        }
        // 账号不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0){
            throw new CustomException(ErrorCode.PARAM_NULL, "账号已存在");
        }
        // 密码和校验密码相同
        if (!checkPassword.equals(password)) {
            throw new CustomException(ErrorCode.PARAM_NULL, "两次密码不一致");
        }
        // 星球号不能重复
        QueryWrapper<User> planetIdQueryWrapper = new QueryWrapper<>();
        planetIdQueryWrapper.eq("planetId", planetId);
        long planetIdCount = this.count(planetIdQueryWrapper);
        if (planetIdCount > 0){
            throw new CustomException(ErrorCode.PARAM_NULL, "星球号已存在");
        // 星球号只能是数字
        } else if (!(planetId.matches("\\d+"))) {
            throw new CustomException(ErrorCode.PARAM_NULL, "星球号只能是数字");
        }

        // 2. 加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = new User(userAccount, newPassword, planetId);
        // 3. 插入数据
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new CustomException(ErrorCode.PARAM_NULL, "注册失败");
        }
        return user.getUserId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyEmpty(userAccount, password)) {
            throw new CustomException(ErrorCode.PARAM_NULL, "账号和密码不能为空");
        }
        if (userAccount.length() < 4) {
            throw new CustomException(ErrorCode.PARAM_NULL, "账号长度不能小于4位");
        }
        if (password.length() < 8){
            throw new CustomException(ErrorCode.PARAM_NULL, "密码长度不能小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?？~！@#￥%……&*（）——+|{}【】‘；：“”’。，·《》]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new CustomException(ErrorCode.PARAM_NULL, "账号只支持数字和英文");
        }
        // 2. 校验密码是否输入正确,和数据库中的密文密码去对比
        String newPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 账号是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        userQueryWrapper.eq("password", newPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new CustomException(ErrorCode.NULL_PARAM_ERROR, "账号不存在/账号密码错误");
        }
        // 3.用户信息脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @Override
    public Integer userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new CustomException(ErrorCode.PARAM_NULL,"用户已退出");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public User getSafetyUser(User user) {
        if (user == null){
            throw new CustomException(ErrorCode.NULL_PARAM_ERROR, "用户信息为空，无法脱敏");
        }
        User safetyUser = new User();
        safetyUser.setUserId(user.getUserId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setPlanetId(user.getPlanetId());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }
}




