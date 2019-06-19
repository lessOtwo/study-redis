package cn.wensheng.studyredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class RedisService
{
    @Autowired
    RedisTemplate<String, Object> jsonRedisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate protobufRedisTemplate;

    public Object getObject(String key)
        throws Exception
    {
        return jsonRedisTemplate.opsForValue().get(key);
    }

    public String getString(String key)
        throws Exception
    {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setObject(String key, Object object)
        throws Exception
    {
        jsonRedisTemplate.opsForValue().set(key, object);
    }

    public void setObjectWithInvalidTime(String key, Object object, long seconds)
    {
        Instant now = Instant.now();
        Instant expires = now.plusSeconds(seconds);
        jsonRedisTemplate.opsForValue().set(key, object, Duration.between(now, expires));
    }

    public void setString(String key, String value)
        throws Exception
    {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setStringWithInvalidTime(String key, String value, long seconds)
    {
        Instant now = Instant.now();
        Instant expires = now.plusSeconds(seconds);
        stringRedisTemplate.opsForValue().set(key, value, Duration.between(now, expires));
    }

    public void del(String key)
        throws Exception
    {
        jsonRedisTemplate.delete(key);
    }
}
