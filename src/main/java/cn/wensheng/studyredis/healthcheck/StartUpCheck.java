package cn.wensheng.studyredis.healthcheck;

import cn.wensheng.studyredis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动检查
 */
@Component
public class StartUpCheck implements ApplicationListener<ContextRefreshedEvent>
{
    private final Logger logger = LoggerFactory.getLogger(StartUpCheck.class);

    @Autowired
    RedisService redisService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        try
        {
            redisService.getString("owner");
            System.out.println("=========>>>connect to redis-cluster success!<<<=========");
        }
        catch (Exception e)
        {
            System.out.println("=========>>>connect to redis-cluster failed!<<<=========");
        }
    }
}
