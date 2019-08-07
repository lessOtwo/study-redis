package cn.wensheng.studyredis;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class AnyInThisTest
{

    @Test
    public void test()
    {
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(2019, 6, 19);
        long days = ChronoUnit.DAYS.between(startDate, today);
        System.out.println(days);
    }
}
