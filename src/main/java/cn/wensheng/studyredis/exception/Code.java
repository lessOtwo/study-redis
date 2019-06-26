package cn.wensheng.studyredis.exception;

public class Code
{
    private Code()
    {
    }

    public static final int UNKNOWN_SERIALIZE_TYPE = 100001;

    public static final String UNKNOWN_SERIALIZE_TYPE_MSG = "未知的缓存序列化方式";

    public static final int CACHEVALUE_CLASS_NOTMATCH = 100002;

    public static final String CACHEVALUE_CLASS_NOTMATCH_MSG = "缓存值与配置的缓存序列化方式不匹配";

    public static final int PARAM_ERROR = 9999;

    public static final String PARAM_ERROR_MSG = "请求参数错误";
}
