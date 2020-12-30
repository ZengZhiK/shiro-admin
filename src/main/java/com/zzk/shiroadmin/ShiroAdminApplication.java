package com.zzk.shiroadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zzk.shiroadmin.mapper")
public class ShiroAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroAdminApplication.class, args);
    }

}
