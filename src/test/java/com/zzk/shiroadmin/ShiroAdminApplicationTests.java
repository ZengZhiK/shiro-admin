package com.zzk.shiroadmin;

import com.zzk.shiroadmin.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroAdminApplicationTests {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        System.out.println(redisUtils);
        redisUtils.set("name", "zhangsan");
        System.out.println(redisUtils.get("name"));
    }
}
