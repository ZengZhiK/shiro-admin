package com.zzk.shiroadmin.cache;

import com.zzk.shiroadmin.common.utils.ApplicationContextUtils;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import org.apache.ibatis.cache.Cache;

/**
 * MyBatis 二级缓存
 *
 * @author zzk
 * @create 2021-02-13 20:29
 */
public class RedisCache implements Cache {
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    // 缓存中放入值
    @Override
    public void putObject(Object key, Object value) {
        getRedisUtils().hset(id, key.toString(), value);
    }

    // 缓存中获取值
    @Override
    public Object getObject(Object key) {
        return getRedisUtils().hget(id, key.toString());
    }

    // 这个方法为mybatis保留方法 默认没有实现
    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        getRedisUtils().delete(id);
    }

    @Override
    public int getSize() {
        return getRedisUtils().hsize(id).intValue();
    }

    private RedisUtils getRedisUtils() {
        return (RedisUtils) ApplicationContextUtils.getBean("redisUtils");
    }
}
