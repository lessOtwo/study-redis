package cn.wensheng.studyredis.cache;

import cn.wensheng.studyredis.exception.Code;
import cn.wensheng.studyredis.exception.WithCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheDao
{
    private static final Logger logger = LoggerFactory.getLogger(CacheDao.class);

    RedisCache redisCache;

    /**
     * 缓存序列化方式
     */
    SerializeTypeEnum serializeType;

    /**
     * 缓存失效时间
     */
    long timeExpire;

    /**
     * 缓存key前缀
     */
    String keyPrefix;

    /**
     * 缓存名
     */
    String cacheName;

    /**
     * 数据获取对象，一般为数据库的DAO
     */
    DBDataLoader dataLoader;

    public void put(String key, Object value)
    {
        try
        {
            switch (serializeType)
            {
                case STRING:
                    if (value instanceof String)
                        redisCache.setStringWithInvalidTime(key, (String)value, timeExpire);
                    else
                        throw new WithCodeException(Code.CACHEVALUE_CLASS_NOTMATCH, Code.CACHEVALUE_CLASS_NOTMATCH_MSG);
                    break;

                case JSON:
                    redisCache.setJsonObjectWithInvalidTime(key, value, timeExpire);
                    break;

                case BYTES:
                    if (value instanceof byte[])
                        redisCache.setBytesWithInvalidTime(key, (byte[])value, timeExpire);
                    else
                        throw new WithCodeException(Code.CACHEVALUE_CLASS_NOTMATCH, Code.CACHEVALUE_CLASS_NOTMATCH_MSG);
                    break;

                default:
                    throw new WithCodeException(Code.UNKNOWN_SERIALIZE_TYPE, Code.UNKNOWN_SERIALIZE_TYPE_MSG);
            }
        }
        catch (Exception e)
        {
            logger.error("putCache===>缓存名:{}, 序列化方式:{}, 缓存key:{}, 缓存value:{}.",
                cacheName, serializeType, key, value, e);
        }
    }

    public Object get(String key, Object[] loadParams)
    {
        Object value = null;
        try
        {
            switch (serializeType)
            {
                case STRING:
                    value = redisCache.getString(key);
                    break;

                case JSON:
                    value = redisCache.getJsonObject(key);
                    break;

                case BYTES:
                    value = redisCache.getBytes(key);
                    break;

                default:
            }
            if (null == value)
            {
                value = fetchNewValueSync(key, loadParams);
            }
        }
        catch (Exception e)
        {
            logger.error("getCache===>缓存名:{}, 序列化方式:{}, 缓存key:{}, 缓存value:{}.",
                cacheName, serializeType, key, value, e);
        }

        return value;
    }

    private Object fetchNewValueSync(String key, Object[] loadParams)
        throws Exception
    {
        DataLoadResult result = dataLoader.load(loadParams);
        if (result.isSuccess())
        {
            Object newVal = result.getData();
            put(key, newVal);
            return newVal;
        }
        return null;
    }

    public RedisCache getRedisCache()
    {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache)
    {
        this.redisCache = redisCache;
    }

    public SerializeTypeEnum getSerializeType()
    {
        return serializeType;
    }

    public void setSerializeType(SerializeTypeEnum serializeType)
    {
        this.serializeType = serializeType;
    }

    public long getTimeExpire()
    {
        return timeExpire;
    }

    public void setTimeExpire(long timeExpire)
    {
        this.timeExpire = timeExpire;
    }

    public String getKeyPrefix()
    {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix)
    {
        this.keyPrefix = keyPrefix;
    }

    public String getCacheName()
    {
        return cacheName;
    }

    public void setCacheName(String cacheName)
    {
        this.cacheName = cacheName;
    }

    public DBDataLoader getDataLoader()
    {
        return dataLoader;
    }

    public void setDataLoader(DBDataLoader dataLoader)
    {
        this.dataLoader = dataLoader;
    }

}
