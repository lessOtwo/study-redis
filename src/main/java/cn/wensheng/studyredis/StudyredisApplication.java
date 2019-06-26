package cn.wensheng.studyredis;

import cn.wensheng.studyredis.cache.CacheDaoFactory;
import cn.wensheng.studyredis.cache.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
public class StudyredisApplication extends SpringBootServletInitializer
    implements ApplicationListener<ContextRefreshedEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(StudyredisApplication.class);

    @Autowired
    RedisCache redisCache;

    @Autowired
    CacheDaoFactory cacheDaoFactory;

    @Value("${cache.config.file}")
    private String cacheConfigFile;

    public static void main(String[] args)
    {
        SpringApplication.run(StudyredisApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        // 检查与Redis集群的连通性
        checkRedisConnect();

        // 初始化缓存Group
        cacheDaoFactory.init(cacheConfigFile);
    }

    private void checkRedisConnect()
    {
        try
        {
            redisCache.getString("owner");
            logger.error("=========>>>connect to redis-cluster success!<<<=========");
        }
        catch (Exception e)
        {
            logger.error("=========>>>connect to redis-cluster failed!<<<=========", e);
        }
    }
}
