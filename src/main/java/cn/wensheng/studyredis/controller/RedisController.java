package cn.wensheng.studyredis.controller;

import cn.wensheng.studyredis.service.RedisService;
import cn.wensheng.studyredis.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController
{
    private final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    RedisService redisService;

    @RequestMapping(method = RequestMethod.GET, value = "/jedis")
    public String get(@RequestParam(required = false, defaultValue = "owner") String key)
    {
        return redisService.get(key);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jedis")
    public String set(@RequestParam String key, @RequestParam String clzName, @RequestBody String body)
    {
        try
        {
            redisService.set(key, StringTools.jsonToObject(body, Class.forName(clzName)));
            return "";
        }
        catch (Exception e)
        {
            logger.error("RedisController.set() has error.", e);
            return "FAIL";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/jedis")
    public void delete(@RequestParam String key)
    {
        redisService.del(key);
    }

}
