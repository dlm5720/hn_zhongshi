package com.hnzs.util;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;//spring封装好的

    public void writeDateToRedis(String loginName, String token){
        redisTemplate.opsForValue().set(loginName,token);
    }

}
