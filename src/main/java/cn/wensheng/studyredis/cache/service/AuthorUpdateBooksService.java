package cn.wensheng.studyredis.cache.service;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.CacheDao;
import cn.wensheng.studyredis.cache.CacheDaoFactory;
import cn.wensheng.studyredis.cache.CacheUtil;

import java.util.List;

public class AuthorUpdateBooksService
{
    private static final String CACHENAME = "AUTHOR_UPDATEBOOKS_CACHE";

    private static final AuthorUpdateBooksService instance = new AuthorUpdateBooksService();

    private AuthorUpdateBooksService()
    {
    }

    public static AuthorUpdateBooksService getInstance()
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
        StringBuilder sb = new StringBuilder(CacheUtil.getKeyPrefix(CACHENAME));
        sb.append(authorId);
        sb.append(":");
        sb.append(timeLimit);
        return sb.toString();
    }
}

