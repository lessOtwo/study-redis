package cn.wensheng.studyredis.mapper;

import cn.wensheng.studyredis.bean.UpdateBook;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorUpdateBooksMapper
{
    List<UpdateBook> getAuthorUpdateBooks(UpdateBook updateBook);
}
