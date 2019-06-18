package cn.wensheng.studyredis;

import cn.wensheng.studyredis.bean.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test
{

    public static void main(String[] args) throws Exception
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountName("文升");
        userInfo.setAge(26);
        userInfo.setMobile("+8617605884922");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(userInfo));


    }
}
