package cn.wensheng.studyredis.service;

import cn.wensheng.studyredis.bean.UserInfo;
import cn.wensheng.studyredis.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService
{
    private final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate<String, Object> jsonRedisTemplate;

    public String get(String key)
    {
        try
        {
            return StringTools.objToJson(jsonRedisTemplate.opsForValue().get(key));
        }
        catch (Exception e)
        {
            logger.error("RedisService.get({}) has error.", key, e);
            return "FAIL";
        }
    }

    public void set(String key, Object object)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter RedisService.set(), key=[{}], value=[{}]", key, object);
        }

        jsonRedisTemplate.opsForValue().set(key, object);
    }

    public void del(String key)
    {
        try
        {
            jsonRedisTemplate.delete(key);
        }
        catch (Exception e)
        {
            logger.error("RedisService.delete({}) has error.", key, e);
        }
    }
}
