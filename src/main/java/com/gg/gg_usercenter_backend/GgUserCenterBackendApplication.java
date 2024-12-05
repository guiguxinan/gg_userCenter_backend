package com.gg.gg_usercenter_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gg.gg_usercenter_backend.mapper")
public class GgUserCenterBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgUserCenterBackendApplication.class, args);
    }

}
