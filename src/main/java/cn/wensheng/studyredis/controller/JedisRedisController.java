package cn.wensheng.studyredis.controller;

import cn.wensheng.studyredis.service.JedisRedisService;
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
public class JedisRedisController
{
    private final Logger logger = LoggerFactory.getLogger(JedisRedisController.class);

    @Autowired
    JedisRedisService jedisRedisService;

    @RequestMapping(method = RequestMethod.GET, value = "/jedis")
    public String get(@RequestParam(required = false, defaultValue = "owner") String key)
    {
        return jedisRedisService.get(key);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jedis")
    public String set(@RequestParam String key, @RequestParam String clzName, @RequestBody String body)
    {
        try
        {
            jedisRedisService.set(key, StringTools.jsonToObject(body, Class.forName(clzName)));
            return "";
        }
        catch (Exception e)
        {
            logger.error("JedisRedisController.set() has error.", e);
            return "FAIL";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/jedis")
    public void delete(@RequestParam(required = true) String key)
    {
        jedisRedisService.del(key);
    }

}
