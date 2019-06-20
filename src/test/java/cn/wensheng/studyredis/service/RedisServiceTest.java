package cn.wensheng.studyredis.service;

import cn.wensheng.studyredis.proto.UserInfoOuterClass.*;
import cn.wensheng.studyredis.utils.ProtobufUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest
{

    @Autowired
    RedisService redisService;

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
        Assert.assertNotNull(getFromRedis);
        Assert.assertEquals(getFromRedis.getAccountName(), "李四");
        Assert.assertEquals(getFromRedis.getMobile(), "+8617605884944");
        Assert.assertEquals(getFromRedis.getAge(), 30);

        System.out.println(ProtobufUtils.printToString(getFromRedis));
    }
}