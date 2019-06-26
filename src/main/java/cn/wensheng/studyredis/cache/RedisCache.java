package cn.wensheng.studyredis.cache;

import cn.wensheng.studyredis.utils.ProtobufUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class RedisCache
{
    @Autowired
    RedisTemplate<String, Object> jsonRedisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, byte[]> bytesRedisTemplate;

    public Object getJsonObject(String key)
        throws Exception
    {
        return jsonRedisTemplate.opsForValue().get(key);
    }

    public String getString(String key)
        throws Exception
    {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public byte[] getBytes(String key)
        throws Exception
    {
        return bytesRedisTemplate.opsForValue().get(key);
    }

    public <T> T getBytes(String key, Class<T> clazz)
        throws Exception
    {
        return (T)ProtobufUtils.deSerialize(bytesRedisTemplate.opsForValue().get(key), clazz);
    }

    public void setJsonObject(String key, Object object)
        throws Exception
    {
        jsonRedisTemplate.opsForValue().set(key, object);
    }

    public void setJsonObjectWithInvalidTime(String key, Object object, long seconds)
        throws Exception
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
        throws Exception
    {
        Instant now = Instant.now();
        Instant expires = now.plusSeconds(seconds);
        stringRedisTemplate.opsForValue().set(key, value, Duration.between(now, expires));
    }

    public void setBytes(String key, byte[] bytes)
        throws Exception
    {
        bytesRedisTemplate.opsForValue().set(key, bytes);
    }

    public void setBytesWithInvalidTime(String key, byte[] bytes, long seconds)
        throws Exception
    {
        Instant now = Instant.now();
        Instant expires = now.plusSeconds(seconds);
        bytesRedisTemplate.opsForValue().set(key, bytes, Duration.between(now, expires));
    }

    public <T> void setBytes(String key, T t, Class<T> clazz)
        throws Exception
    {
        setBytes(key, ProtobufUtils.serialize(t, clazz));
    }

    public <T> void setBytesWithInvalidTime(String key, T t, Class<T> clazz, long seconds)
        throws Exception
    {
        setBytesWithInvalidTime(key, ProtobufUtils.serialize(t, clazz), seconds);
    }

    public void del(String key)
        throws Exception
    {
        stringRedisTemplate.delete(key);
    }
}
