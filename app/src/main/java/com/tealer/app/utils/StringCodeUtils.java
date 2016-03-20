package com.tealer.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author：pengbo on 2016/3/20 17:04
 * Email：1162947801@qq.com
 */
public class StringCodeUtils {
    /**
     *
     * @方法名称:toUTF8
     * @描述: String 转为UTF8编码格式
     * @创建人：jwl
     * @创建时间：2014年7月28日 下午2:05:15
     * @param @param s
     * @param @return
     * @返回类型：String
     * @throws
     */
    public static String toUTF8(String s)
    {
        if (s == null || s.equals(""))
        {
            return "";
        }
        else
        {
            try
            {
                return URLEncoder.encode(s, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 返回中英文混合计算的字符串长度(中文算两个字符)
     * @param s
     * @return
     */
    public static int getLen(String s)
    {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            count++;
            if (isChinese(s.charAt(i)))
                count++;
        }

        return count;
    }

    /**
     *
     *@方法名称:getNormalLength
     *@描述: 获取字符串字符的最大长度
     *@创建人：lipengbo
     *@创建时间：2015年4月1日 上午9:25:02
     *@param @param maxLength
     *@param @param s
     *@param @return
     *@返回类型：int
     *@throws
     */
    public static int getNormalLength(int maxLength, String s){
        int count=0;
        int normalLength=0;
        for (int i = 0; i < s.length(); i++) {
            count++;
            normalLength++;
            if (isChinese(s.charAt(i)))
                count++;
            if(count>=maxLength)
                return normalLength;
        }
        return normalLength;
    }



    /**
     *
     *@方法名称:isChinese
     *@描述:判断字符串是否是汉字
     *@创建人：lipengbo
     *@创建时间：2014年10月16日 上午10:51:39
     *@param @param s
     *@param @return
     *@返回类型：boolean
     *@throws
     */
    public static boolean isChinese(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!isChinese(s.charAt(i))){
                return false;
            }
        }
        return true;
    }




    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        //CJK的意思是“Chinese，Japanese，Korea”的简写 ，实际上就是指中日韩三国的象形文字的Unicode编码
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS                        //4E00-9FBF：CJK 统一表意符号
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS           //F900-FAFF：CJK 兼容象形文字
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A     //统一表意符号扩展A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B     //统一表意符号扩展B
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS          //FF00-FFEF：半角及全角形式
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION                    //2000-206F：常用标点
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION            //3000-303F：CJK 符号和标点
                ) {
            return true;
        }
        return false;
    }
}
