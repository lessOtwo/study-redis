package cn.wensheng.studyredis.cache.dataloader;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.DBDataLoader;
import cn.wensheng.studyredis.cache.DataLoadResult;
import cn.wensheng.studyredis.exception.Code;
import cn.wensheng.studyredis.exception.WithCodeException;
import cn.wensheng.studyredis.mapper.AuthorUpdateBooksMapper;
import cn.wensheng.studyredis.utils.SpringUtils;
import io.netty.util.internal.StringUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuthorUpdateBooksDataloader implements DBDataLoader
{
    @Override
    public DataLoadResult load(Object[] loadParams)
        throws Exception
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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        param.setFromDate(formatter.format(now.minusHours(timeLimit)));

        AuthorUpdateBooksMapper authorUpdateBooksMapper = SpringUtils.getBean(AuthorUpdateBooksMapper.class);
        List<UpdateBook> updateBookList = authorUpdateBooksMapper.getAuthorUpdateBooks(param);

        result.setSuccess(null != updateBookList);
        result.setData(updateBookList);
        return result;
    }
}
