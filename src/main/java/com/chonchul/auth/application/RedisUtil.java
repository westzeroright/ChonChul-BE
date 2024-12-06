package com.chonchul.auth.application;

import com.chonchul.auth.application.service.EmailStatus;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    public String getData(String key){
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, EmailStatus emailStatus){
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set(key,emailStatus.toString());
    }

    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        Duration expireDuration=Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }

    public void deleteData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }
}
