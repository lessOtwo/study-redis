package cn.wensheng.studyredis.service;

import cn.wensheng.studyredis.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class JedisRedisService
{
    private final Logger logger = LoggerFactory.getLogger(JedisRedisService.class);

    @Autowired
    RedisTemplate redisTemplate;

    public String get(String key)
    {
        try
        {
            Object obj = redisTemplate.opsForValue().get(key);
            return StringTools.objToJson(obj);
        }
        catch (Exception e)
        {
            logger.error("JedisRedisService.get() has error.", e);
            return "FAIL";
        }
    }

    public void set(String key, Object object)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter JedisRedisService.set(), key=[{}], value=[{}]", key, object);
        }

        redisTemplate.opsForValue().set(key, object);

    }

    public void del(String key)
    {
        try
        {
            redisTemplate.delete(key);
        }
        catch (Exception e)
        {
            logger.error("JedisRedisService.delete() has error.", e);
        }
    }
}
