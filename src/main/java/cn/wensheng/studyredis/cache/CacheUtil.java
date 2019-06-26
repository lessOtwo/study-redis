package cn.wensheng.studyredis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheUtil
{
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);

    public static Object getCachedData(String cacheName, String key, Object[] loadParams)
    {
        CacheDao dao = CacheDaoFactory.getCacheDao(cacheName);
        if (null == dao)
        {
            logger.error("缓存:{}获取CacheDao失败.", cacheName);
            return null;
        }
        else
        {
            return dao.get(key, loadParams);
        }
    }

    public static String getKeyPrefix(String cacheName)
    {
        CacheDao dao = CacheDaoFactory.getCacheDao(cacheName);
        if (null == dao)
        {
            logger.error("缓存:{}获取CacheDao失败.", cacheName);
            return null;
        }
        else
        {
            return dao.getKeyPrefix();
        }
    }
}
