package cn.wensheng.studyredis.controller;

import cn.wensheng.studyredis.service.RedisService;
import cn.wensheng.studyredis.utils.NumTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisController
{
    private final Logger logger = LoggerFactory.getLogger(RedisController.class);

    private static final String FAILED = "FAIL~!";

    private static final String SUCCESS = "OK~!";

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RedisService redisService;

    @GetMapping(value = "/redis")
    public String get(@RequestParam boolean serialize,
        @RequestParam(required = false, defaultValue = "owner") String key)
    {
        try
        {
            if (serialize)
            {
                return objectMapper.writeValueAsString(redisService.getObject(key));
            }
            else
            {
                return redisService.getString(key);
            }
        }
        catch (Exception e)
        {
            logger.error("RedisController.get({}) has error.", key, e);
            return FAILED;
        }
    }

    @PostMapping(value = "/redis")
    public String set(@RequestParam String key, @RequestParam(required = false) String clzName,
        @RequestBody String body, @RequestParam(required = false) String seconds)
    {
        try
        {
            if (StringUtil.isNullOrEmpty(seconds))
            {
                if (StringUtil.isNullOrEmpty(clzName))
                {
                    redisService.setString(key, body);
                }
                else
                {
                    redisService.setObject(key, objectMapper.readValue(body, Class.forName(clzName)));
                }
            }
            else
            {
                long invalidTime = NumTools.toLong(seconds, 60);
                if (StringUtil.isNullOrEmpty(clzName))
                {
                    redisService.setStringWithInvalidTime(key, body, invalidTime);
                }
                else
                {
                    redisService.setObjectWithInvalidTime(key,
                        objectMapper.readValue(body, Class.forName(clzName)),
                        invalidTime);
                }
            }

            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error("RedisController.set({}) has error.", key, e);
            return FAILED;
        }
    }

    @DeleteMapping(value = "/redis")
    public String delete(@RequestParam String key)
    {
        try
        {
            redisService.del(key);
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error("RedisController.delete({}) has error.", key, e);
            return FAILED;
        }
    }

}
