package cn.wensheng.studyredis.cache.dataloader;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.DBDataLoader;
import cn.wensheng.studyredis.cache.DataLoadResult;
import cn.wensheng.studyredis.exception.Code;
import cn.wensheng.studyredis.exception.WithCodeException;
import cn.wensheng.studyredis.dao.AuthorUpdateBooksDao;
import cn.wensheng.studyredis.utils.SpringUtils;
import io.netty.util.internal.StringUtil;

import java.sql.SQLException;
import java.util.List;

public class AuthorUpdateBooksDataloader implements DBDataLoader
{
    @Override
    public DataLoadResult load(Object[] loadParams)
        throws SQLException, WithCodeException
    {
        DataLoadResult result = new DataLoadResult();

        String authorId = null;
        int timeLimit = 0;

        if (null != loadParams && loadParams.length == 2)
        {
            authorId = (String)loadParams[0];
            timeLimit = (int)loadParams[1];
        }

        if (StringUtil.isNullOrEmpty(authorId) || timeLimit == 0)
        {
            throw new WithCodeException(Code.PARAM_ERROR, Code.PARAM_ERROR_MSG);
        }

        UpdateBook param = new UpdateBook();
        param.setAuthorId(authorId);
        param.setTimeLimit(timeLimit);

        AuthorUpdateBooksDao authorUpdateBooksDao = SpringUtils.getBean(AuthorUpdateBooksDao.class);
        List<UpdateBook> updateBookList = authorUpdateBooksDao.getAuthorUpdateBooks(param);

        result.setSuccess(null != updateBookList);
        result.setData(updateBookList);
        return result;
    }
}
