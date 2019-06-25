package cn.wensheng.studyredis.service;

import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootTest
public class AnyInThisTest
{

    @Test
    public void test()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(now.plusHours(2)));
    }
}
