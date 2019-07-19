package cn.wensheng.studyredis.dao;

import cn.wensheng.studyredis.bean.UpdateBook;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AuthorUpdateBooksDao
{
    List<UpdateBook> getAuthorUpdateBooks(UpdateBook updateBook) throws SQLException;
}
