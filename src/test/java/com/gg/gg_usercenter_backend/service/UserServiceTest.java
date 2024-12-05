package com.gg.gg_usercenter_backend.service;

import com.gg.gg_usercenter_backend.model.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.SimpleDateFormat;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @SneakyThrows
    @Test
    void save() {
        User user = new User();
        user.setUsername("贺文轩");
        user.setUserAccount("罗文");
        user.setAvatarUrl("www.homer-lind.name");
        user.setEmail("111@163.com");
        user.setGender(1);
        user.setIsDelete(0);
        user.setPhone("12321312321");
        user.setUserStatus(0);
        user.setUserRole(1);
        user.setPassword("vMPaUjT&MHYZSxW+w**8ee8ZGDhmZ9Xca");
        user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-07 10:05:03"));
        user.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-06 09:00:21"));
        userService.save(user);
        System.out.println("新增加数据id为：" + user.getUserId());
        Assertions.assertTrue(true);
    }
}