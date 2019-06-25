package cn.wensheng.studyredis.service;

import cn.wensheng.studyredis.bean.UpdateBook;
import cn.wensheng.studyredis.mapper.AuthorUpdateBooksMapper;
import cn.wensheng.studyredis.proto.UserInfoOuterClass.*;
import cn.wensheng.studyredis.utils.ProtobufUtils;
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
public class RedisServiceTest
{

    @Autowired
    RedisService redisService;

    @Autowired
    AuthorUpdateBooksMapper authorUpdateBooksMapper;

    @Test
    public void setAndGetBytes()
        throws Exception
    {
        String THIS_KEY = "CPS:380:USERINFO:00000003";

        UserInfo.Builder userInfo = UserInfo.newBuilder();
        userInfo.setAccountName("李四");
        userInfo.setMobile("+8617605884944");
        userInfo.setAge(30);

        redisService.setBytes(THIS_KEY, userInfo.build().toByteArray());
        byte[] bytes = redisService.getBytes(THIS_KEY);
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
}