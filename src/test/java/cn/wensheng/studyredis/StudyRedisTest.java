package cn.wensheng.studyredis;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.RedisCache;
import cn.wensheng.studyredis.cache.service.AuthorUpdateBooksCacheService;
import cn.wensheng.studyredis.dao.AuthorUpdateBooksDao;
import cn.wensheng.studyredis.proto.UserInfoOuterClass.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyRedisTest
{
    private static final int days = (int)ChronoUnit.DAYS.between(LocalDate.of(2019, 6, 19),
        LocalDate.now());

    @Autowired
    RedisCache redisCache;

    @Autowired
    AuthorUpdateBooksDao authorUpdateBooksDao;

    @BeforeClass
    public static void init()
    {
        org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.getInstance();
    }

    @Test
    public void setAndGetBytes()
        throws Exception
    {
        String THIS_KEY = "CPS:380:USERINFO:00000003";

        UserInfo.Builder userInfo = UserInfo.newBuilder();
        userInfo.setAccountName("李四");
        userInfo.setMobile("+8617605884944");
        userInfo.setAge(30);

        redisCache.setBytesWithInvalidTime(THIS_KEY, userInfo.build().toByteArray(), 60);
        byte[] bytes = redisCache.getBytes(THIS_KEY);
        UserInfo getFromRedis = UserInfo.parseFrom(bytes);

        assertNotNull(getFromRedis);
        assertEquals("李四", getFromRedis.getAccountName());
        assertEquals("+8617605884944", getFromRedis.getMobile());
        assertEquals(30, getFromRedis.getAge());
    }

    @Test
    public void testGetAuthorUpdateBooks()
        throws SQLException
    {

        UpdateBook param = new UpdateBook();
        param.setAuthorId("1003755015");
        param.setTimeLimit(days);

        List<UpdateBook> updateBookList = authorUpdateBooksDao.getAuthorUpdateBooks(param);
        assertNotNull(updateBookList);
    }

    @Test
    public void testAuthorUpdateBooksService()
        throws Exception
    {
        String authorId = "1003755015";
        String key = AuthorUpdateBooksCacheService.getInstance().getKey(authorId, days);
        System.out.println(key);

        List<UpdateBook> updateBookList =
            AuthorUpdateBooksCacheService.getInstance().getAuthorUpdateBooks(authorId, days);
        assertNotNull(updateBookList);

        assertNotNull(redisCache.getJsonObject(key));
    }
}