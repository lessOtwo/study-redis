package cn.wensheng.studyredis;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.cache.RedisCache;
import cn.wensheng.studyredis.cache.service.AuthorUpdateBooksService;
import cn.wensheng.studyredis.mapper.AuthorUpdateBooksMapper;
import cn.wensheng.studyredis.proto.UserInfoOuterClass.*;
import cn.wensheng.studyredis.utils.ProtobufUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyRedisTest
{

    @Autowired
    RedisCache redisCache;

    @Autowired
    AuthorUpdateBooksMapper authorUpdateBooksMapper;

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
        assertEquals(getFromRedis.getAccountName(), "李四");
        assertEquals(getFromRedis.getMobile(), "+8617605884944");
        assertEquals(getFromRedis.getAge(), 30);

        System.out.println(ProtobufUtils.printToString(getFromRedis));
    }

    @Test
    public void testGetAuthorUpdateBooks()
    {
        UpdateBook param = new UpdateBook();
        param.setAuthorId("1003755015");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        param.setFromDate(formatter.format(now.minusHours(168)));

        List<UpdateBook> updateBookList = authorUpdateBooksMapper.getAuthorUpdateBooks(param);
        assertNotNull(updateBookList);
    }

    @Test
    public void testAuthorUpdateBooksService()
        throws Exception
    {
        String authorId = "1003755015";
        int timeLimit = 168;
        String key = AuthorUpdateBooksService.getInstance().getKey(authorId, timeLimit);
        System.out.println(key);

        List<UpdateBook> updateBookList =
            AuthorUpdateBooksService.getInstance().getAuthorUpdateBooks(authorId, timeLimit);
        assertNotNull(updateBookList);
        System.out.println(updateBookList);

        assertNotNull(redisCache.getJsonObject(key));
    }
}