package cn.wensheng.studyredis.cache;

/**
 * 从数据库查询输入放入缓存加载类
 */
public class DataLoadResult
{
    /**
     * 标识缓存是否放入成功标识
     */
    private boolean success;

    /**
     * 数据
     */
    private Object data;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

}
