package cn.wensheng.studyredis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.Character.UnicodeScript;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class StringTools
{
    private static String[] expectUrls = null;

    private static String[] mmUrls = null;

    private static final ObjectMapper mapper = new ObjectMapper();

    private StringTools()
    {
    }

    public static boolean isDigtial(String str)
    {
        return isEmpty(str) ? false : str.matches("\\d+");
    }

    public static String trim(String str)
    {
        return str == null ? null : str.trim();
    }

    public static boolean isEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    public static String nvl(Object o)
    {
        return null == o ? "" : o.toString().trim();
    }

    public static String getTrim(String str, String def)
    {
        if (str == null)
        {
            return def;
        }
        else
        {
            String t = str.trim();
            return t.length() == 0 ? def : t;
        }
    }

    public static String subString(String str, int begin, int end)
    {
        if (isEmpty(str))
        {
            return str;
        }
        else
        {
            int b = Math.max(begin, 0);
            int e = Math.min(end, str.length());
            return str.substring(b, e);
        }
    }

    public static String replaceBacklash(String str)
    {
        return isEmpty(str) ? "" : str.replace("\\", "/");
    }

    public static String replace2amp(String text)
    {
        return text2Html(text);
    }

    public static String replace(String text, String find, String replace)
    {
        if (text != null && find != null && replace != null)
        {
            int findLen = find.length();
            int textLen = text.length();
            if (textLen != 0 && findLen != 0)
            {
                StringBuffer sb = new StringBuffer(256);
                int begin = 0;

                for (int i = text.indexOf(find); i != -1; i = text.indexOf(find, begin))
                {
                    sb.append(text.substring(begin, i));
                    sb.append(replace);
                    begin = i + findLen;
                }

                if (begin < textLen)
                {
                    sb.append(text.substring(begin));
                }

                return sb.toString();
            }
            else
            {
                return text;
            }
        }
        else
        {
            return text;
        }
    }

    public static String replaceChar(String str)
    {
        if (null == str)
        {
            str = "";
        }

        str = replace(str, "&", "&amp;");
        str = replace(str, "'", "&apos;");
        str = replace(str, "<<", "&lt;&lt;");
        str = replace(str, ">>", "&gt;&gt;");
        return str;
    }

    public static String text2Html(String text)
    {
        text = replace(text, "&amp;", "&");
        text = replace(text, "&lt;", "<");
        text = replace(text, "&gt;", ">");
        text = replace(text, "&quot;", "\"");
        text = replace(text, "&apos;", "'");
        text = replace(text, "&", "&amp;");
        text = replace(text, "<", "&lt;");
        text = replace(text, ">", "&gt;");
        text = replace(text, "\"", "&quot;");
        text = replace(text, "'", "&apos;");
        return text;
    }

    public static String html2Text(String text)
    {
        text = replace(text, "&lt;", "<");
        text = replace(text, "&gt;", ">");
        text = replace(text, "&amp;", "&");
        text = replace(text, "&quot;", "\"");
        text = replace(text, "&apos;", "'");
        return text;
    }

    private static String replace$(String instr)
    {
        StringBuffer sb = new StringBuffer(instr);

        for (int place = sb.indexOf("$"); place >= 0; place = sb.indexOf("$", place + 2))
        {
            sb.replace(place, place + 1, "$$");
        }

        return sb.toString();
    }

    public static String getStrByLengthForShort(String str, int len, String elide)
    {
        if (str == null)
        {
            return "";
        }
        else
        {
            byte[] strByte = str.getBytes();
            int strLen = strByte.length;
            if (len >= strLen)
            {
                return str;
            }
            else
            {
                elide = elide == null ? "" : elide;
                int elideLen = elide.getBytes().length;
                if (len <= elideLen)
                {
                    return "";
                }
                else
                {
                    len -= elideLen;
                    int count = 0;

                    for (int i = 0; i < len; ++i)
                    {
                        int value = strByte[i];
                        if (value < 0)
                        {
                            ++count;
                        }
                    }

                    if (count % 2 != 0)
                    {
                        --len;
                    }

                    String subString = new String(strByte, 0, len);
                    subString = isEmpty(subString) ? "" : subString + elide;
                    return subString;
                }
            }
        }
    }

    public static boolean matches(String base, String[] matches)
    {
        if (null != matches && 0 != matches.length)
        {
            for (int i = 0; i < matches.length; ++i)
            {
                if (matches[i].equals(base))
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    public static float toFloat(String s, float def)
    {
        float f;
        try
        {
            f = Float.parseFloat(s);
        }
        catch (Exception var4)
        {
            f = def;
        }

        return f;
    }

    public static int toInt(String s, int def)
    {
        int value;
        try
        {
            value = Integer.parseInt(s);
        }
        catch (Exception var4)
        {
            value = def;
        }

        return value;
    }

    public static double toDouble(String s, double def)
    {
        double value;
        try
        {
            value = Double.parseDouble(s);
        }
        catch (Exception var6)
        {
            value = def;
        }

        return value;
    }

    public static String getSum(String s1, String s2)
    {
        double s3 = toDouble(s1, 0.0D);
        double s4 = toDouble(s2, 0.0D);
        return String.valueOf(s3 + s4);
    }

    public static String getSub(String s1, String s2)
    {
        double s3 = toDouble(s1, 0.0D);
        double s4 = toDouble(s2, 0.0D);
        return String.valueOf(s3 - s4);
    }

    public static Long toLong(String s, long def)
    {
        long value;
        try
        {
            value = Long.parseLong(s);
        }
        catch (Exception var6)
        {
            value = def;
        }

        return value;
    }

    public static String getSubstr(String str, int length)
    {
        if (null == str)
        {
            return "";
        }
        else
        {
            return str.length() > length ? str.substring(0, length) + "..." : str;
        }
    }

    public static String removeZeroAfterDecimalPoint(String data)
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        return nf.format(Double.valueOf(data));
    }

    public static String convertDiscount(String discount)
    {
        if (discount != null && !"".equals(discount.trim()))
        {
            return discount.endsWith("0") ? discount.substring(0, discount.length() - 1) : discount;
        }
        else
        {
            return "";
        }
    }

    public static String replacs(String s, int begin, int end, String c)
    {
        if (null != s)
        {
            int len = s.length();
            if (end > begin && len > begin)
            {
                if (len < end)
                {
                    end = len;
                }

                if (begin < 0)
                {
                    begin = 0;
                }

                StringBuffer sb;
                for (sb = new StringBuffer(s.substring(0, begin)); begin < end; ++begin)
                {
                    sb.append(c);
                }

                sb.append(s.substring(end));
                s = sb.toString();
            }
        }
        else
        {
            s = "";
        }

        return s;
    }

    public static String fenToYuan(String s, float def)
    {
        float fen = toFloat(s, def);
        String sFen = (int)Math.floor((double)fen) + "";
        int len = sFen.length();
        String yuan;
        if (len == 1)
        {
            yuan = "0.0" + sFen;
        }
        else if (len == 2)
        {
            yuan = "0." + sFen;
        }
        else
        {
            yuan = sFen.substring(0, len - 2) + '.' + sFen.substring(len - 2);
        }

        return fen < 0.0F ? '-' + yuan : yuan;
    }

    public static int getStrLenth(String str)
    {
        if (isEmpty(str))
        {
            return 0;
        }
        else
        {
            int strLength = 0;

            for (int i = 0; i < str.length(); ++i)
            {
                if (str.charAt(i) > 255)
                {
                    strLength += 2;
                }
                else
                {
                    ++strLength;
                }
            }

            return strLength;
        }
    }

    public static boolean isEq(String str1, String str2)
    {
        if (str1 == null)
        {
            return str2 == null;
        }
        else
        {
            return str1.equals(str2);
        }
    }

    public static String nvl2(String tempStr, String def)
    {
        if (isEmpty(tempStr))
        {
            return def == null ? "" : def.trim();
        }
        else
        {
            return tempStr.trim();
        }
    }

    public static String text2HtmlToSend(String text)
    {
        text = replace(text, "&amp;", "&");
        text = replace(text, "&lt;", "<");
        text = replace(text, "&gt;", ">");
        text = replace(text, "&quot;", "\"");
        text = replace(text, "&apos;", "'");
        return text;
    }

    public static String textchange(String text)
    {
        String str1 = text.replace("&nbsp;", "&#160;");
        str1 = str1.replaceAll("&lt;", "&#60;");
        str1 = str1.replaceAll("&gt;", "&#62;");
        str1 = str1.replaceAll("&quot;", "&#34;");
        str1 = str1.replaceAll("&apos;", "&#39;");
        str1 = str1.replaceAll("￠", "&#162;");
        str1 = str1.replaceAll("&cent;", "&#162;");
        str1 = str1.replaceAll("£", "&#163;");
        str1 = str1.replaceAll("&pound;", "&#163;");
        str1 = str1.replaceAll("¥", "&#165;");
        str1 = str1.replaceAll("&yen;", "&#165;");
        str1 = str1.replaceAll("€", "&#8364;");
        str1 = str1.replaceAll("&euro;", "&#8364;");
        str1 = str1.replaceAll("§", "&#167;");
        str1 = str1.replaceAll("&sect;", "&#167;");
        str1 = str1.replaceAll("©", "&#169;");
        str1 = str1.replaceAll("&copy;", "&#169;");
        str1 = str1.replaceAll("®", "&#174;");
        str1 = str1.replaceAll("&reg;", "&#174;");
        str1 = str1.replaceAll("™", "&#8482;");
        str1 = str1.replaceAll("&trade;", "&#8482;");
        str1 = str1.replaceAll("×", "&#215;");
        str1 = str1.replaceAll("&times;", "&#215;");
        str1 = str1.replaceAll("÷", "&#247;");
        str1 = str1.replaceAll("&divide;", "&#247;");
        str1 = str1.replaceAll("&amp;", "&#38;");
        str1 = str1.replaceAll("&", "&#38;");
        str1 = str1.replaceAll("&#38;#38;", "&#38;");
        str1 = str1.replaceAll("&#38;#160;", "&#160;");
        str1 = str1.replaceAll("&#38;#60;", "&#60;");
        str1 = str1.replaceAll("&#38;#62;", "&#62;");
        str1 = str1.replaceAll("&#38;#38;", "&#38;");
        str1 = str1.replaceAll("&#38;#34;", "&#34;");
        str1 = str1.replaceAll("&#38;#39;", "&#39;");
        str1 = str1.replaceAll("&#38;#162;", "&#162;");
        str1 = str1.replaceAll("&#38;#163;", "&#163;");
        str1 = str1.replaceAll("&#38;#165;", "&#165;");
        str1 = str1.replaceAll("&#38;#8364;", "&#8364;");
        str1 = str1.replaceAll("&#38;#167;", "&#167;");
        str1 = str1.replaceAll("&#38;#169;", "&#169;");
        str1 = str1.replaceAll("&#38;#174;", "&#174;");
        str1 = str1.replaceAll("&#38;#8482;", "&#8482;");
        str1 = str1.replaceAll("&#38;#215;", "&#215;");
        str1 = str1.replaceAll("&#38;#247;", "&#247;");
        return str1;
    }

    public static int countStr(String str1, String str2)
    {
        int cnt = 0;

        for (int offset = 0; (offset = str1.indexOf(str2, offset)) != -1; ++cnt)
        {
            offset += str2.length();
        }

        return cnt;
    }

    public static BigDecimal toBigDecimal(String string, String def)
    {
        BigDecimal value = null;

        try
        {
            value = new BigDecimal(string);
        }
        catch (Exception var4)
        {
            value = new BigDecimal(def);
        }

        return value;
    }

    public static double getSubByBD(String string1, String string2)
    {
        BigDecimal first = toBigDecimal(string1, "0");
        BigDecimal second = toBigDecimal(string2, "0");
        return first.subtract(second).doubleValue();
    }

    public static String space2nbsp(String text)
    {
        text = replace(text, "&nbsp;", " ");
        text = replace(text, " ", "&nbsp;");
        return text;
    }

    public static String append2String(String... arrays)
    {
        StringBuilder sb = new StringBuilder();
        String[] var2 = arrays;
        int var3 = arrays.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            String array = var2[var4];
            sb.append(array);
        }

        return sb.toString();
    }

    public static String getString(String org, String def)
    {
        return isEmpty(org) ? def : org;
    }

    public static String replaceSpecialChar(String str)
    {
        if (null == str)
        {
            str = "";
        }

        str = text2Html(str);
        str = replace$(str);
        str = str.replace("{BR}", "<br/>");
        str = str.replace("\r\n", "<br/>");
        return str;
    }

    public static int getChineseNum(String str)
    {
        int count = 0;
        if (isEmpty(str))
        {
            return count;
        }
        else
        {
            char[] chars = str.toCharArray();
            char[] var3 = chars;
            int var4 = chars.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                char c = var3[var5];
                if (isChineseByScript(c) || isChinesePunctuation(c))
                {
                    ++count;
                }
            }

            return count;
        }
    }

    public static boolean isChineseByScript(char c)
    {
        UnicodeScript sc = UnicodeScript.of(c);
        return sc == UnicodeScript.HAN;
    }

    public static boolean isChinesePunctuation(char c)
    {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
            ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.CJK_COMPATIBILITY_FORMS ||
            ub == UnicodeBlock.VERTICAL_FORMS;
    }

    public static String objToJson(Object obj)
        throws JsonProcessingException
    {
        if (null == obj)
            return "";
        return mapper.writeValueAsString(obj);
    }

    public static Object jsonToObject(String jsonStr, Class<?> clz)
        throws IOException
    {
        if(isEmpty(jsonStr) || null == clz)
            return null;
        return mapper.readValue(jsonStr, clz);
    }

}

