package cn.wensheng.studyredis.cache;

import cn.wensheng.studyredis.exception.WithCodeException;

import java.sql.SQLException;

/**
 * 从数据库加载数据放入缓存接口
 */
public interface DBDataLoader
{
    /**
     * 数据库查询数据放入缓存
     */
    DataLoadResult load(Object[] loadParams) throws SQLException, WithCodeException;
}
