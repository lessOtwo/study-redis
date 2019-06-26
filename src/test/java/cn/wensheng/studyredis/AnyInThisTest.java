package cn.wensheng.studyredis;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class AnyInThisTest
{

    @Test
    public void test()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(now.plusHours(2)));

        Object bytes = new byte[8];
        System.out.println(bytes instanceof int[]);
        System.out.println(bytes instanceof byte[]);
    }
}
