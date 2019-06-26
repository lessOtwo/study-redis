package cn.wensheng.studyredis.bean;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private static final long serialVersionUID = -3991598665278483174L;

    private String accountName;

    private String mobile;

    private int age;

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("accountName='").append(accountName).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
