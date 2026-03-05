package com.URL.ScaleLink.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiter {
    private final StringRedisTemplate redisTemplate;

    private static final int MAX_REQUESTS=10;
    private static final int WINDOW_SECONDS=60;

    public boolean isAllowed(String key){
        Long count=redisTemplate.opsForValue().increment(key);

        if(count==1){
            redisTemplate.expire(key,WINDOW_SECONDS, TimeUnit.SECONDS);
        }

        return count<=MAX_REQUESTS;
    }
}
