package cn.wensheng.studyredis.utils;

public class NumTools
{
    private NumTools(){}

    public static long toLong(String number, long def)
    {
        try
        {
            return Long.parseLong(number);
        }
        catch (NumberFormatException e)
        {
            return def;
        }
    }
}
