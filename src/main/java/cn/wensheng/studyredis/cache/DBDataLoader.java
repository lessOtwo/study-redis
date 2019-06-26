package cn.wensheng.studyredis.cache;

/**
 * 从数据库加载数据放入缓存接口
 */
public interface DBDataLoader
{
    /**
     * 数据库查询数据放入缓存
     */
    DataLoadResult load(Object[] loadParams);
}
