package com.gg.gg_usercenter_backend.user;

import com.gg.gg_usercenter_backend.mapper.UserMapper;
import com.gg.gg_usercenter_backend.model.User;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName UserTest
 * @Author GuiGuXiNan
 * @Date 2024/11/10 1:10
 * @Version 1.0
 **/
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
