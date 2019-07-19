package cn.wensheng.studyredis.cache.service;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.CacheUtil;

import java.util.List;

public class AuthorUpdateBooksCacheService
{
    private static final String CACHENAME = "AUTHOR_UPDATEBOOKS_CACHE";

    private static final AuthorUpdateBooksCacheService instance = new AuthorUpdateBooksCacheService();

    private AuthorUpdateBooksCacheService()
    {
    }

    public static AuthorUpdateBooksCacheService getInstance()
    {
        return instance;
    }

    public List<UpdateBook> getAuthorUpdateBooks(String authorId, int timeLimit)
    {
        return (List<UpdateBook>)CacheUtil.getCachedData(CACHENAME,
            getKey(authorId, timeLimit),
            new Object[] {authorId, timeLimit});
    }

    public String getKey(String authorId, int timeLimit)
    {
        return CacheUtil.getKeyPrefix(CACHENAME) + authorId + ":" + timeLimit;
    }
}

