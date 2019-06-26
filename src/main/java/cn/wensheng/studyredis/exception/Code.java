package cn.wensheng.studyredis.exception;

public interface Code
{
    int UNKNOWN_SERIALIZE_TYPE = 100001;
    String UNKNOWN_SERIALIZE_TYPE_MSG = "未知的缓存序列化方式";

    int CACHEVALUE_CLASS_NOTMATCH = 100002;
    String CACHEVALUE_CLASS_NOTMATCH_MSG = "缓存值与配置的缓存序列化方式不匹配";

    int PARAM_ERROR = 9999;
    String PARAM_ERROR_MSG = "请求参数错误";
}
