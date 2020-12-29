package com.zzk.shiroadmin.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 *
 * @author zzk
 * @create 2020-12-21 11:04
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 设置值value的序列化方式
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        // 设置键key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
