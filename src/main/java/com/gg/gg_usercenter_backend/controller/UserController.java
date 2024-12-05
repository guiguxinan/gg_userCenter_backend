package com.gg.gg_usercenter_backend.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gg.gg_usercenter_backend.common.BaseResponse;
import com.gg.gg_usercenter_backend.common.ErrorCode;
import com.gg.gg_usercenter_backend.common.ResultUtils;
import com.gg.gg_usercenter_backend.exception.CustomException;
import com.gg.gg_usercenter_backend.model.domain.User;
import com.gg.gg_usercenter_backend.model.request.UserLoginRequest;
import com.gg.gg_usercenter_backend.model.request.UserRegisterRequest;
import com.gg.gg_usercenter_backend.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static com.gg.gg_usercenter_backend.constant.UserConstant.ADMIN_USER;
import static com.gg.gg_usercenter_backend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @ClassName 用户接口
 * @Author GuiGuXiNan
 * @Date 2024/11/11 17:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest 用户注册参数请求体
     * @return 新用户 id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            throw new CustomException(ErrorCode.PARAM_NULL);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetId = userRegisterRequest.getPlanetId();

        long l = userService.userRegister(userAccount, password, checkPassword, planetId);
        return ResultUtils.success(l);
    }

    /**
     * 用户登录接口
     * @param userLoginRequest 用户注册参数请求体
     * @param request 作为响应的一部分返回给客户端
     * @return 用户信息
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAM_NULL);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAM_NULL);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request){
        Integer i = userService.userLogout(request);
        return ResultUtils.success(i, "退出登录成功");
    }

    /**
     * 用户查询（只限管理员）
     * @param username 用户名
     * @param request 获取用户态
     * @return List<User>
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
        if (!isAdmin(request)) {
            throw new CustomException(ErrorCode.NO_AUTH,"无权限查看");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        // 返回脱敏后的userList
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 用户删除（仅管理员）
     * @param userId 用户id
     * @param request 作为响应的一部分返回给客户端
     * @return boolean
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(@RequestBody long userId, HttpServletRequest request){
        if (!isAdmin(request)){
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        if (userId <= 0){
            throw new CustomException(ErrorCode.PARAM_NULL);
        }
        boolean b = userService.removeById(userId);
        return ResultUtils.success(b);
    }
    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getUserId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }


    /**
     * 是否为管理员或用户
     * @param request request
     * @return boolean
     */
    private boolean isAdmin(HttpServletRequest request){
        // 获取用户态
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_USER;
    }
}
