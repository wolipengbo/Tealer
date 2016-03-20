package com.tealer.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author：pengbo on 2016/3/20 18:27
 * Email：1162947801@qq.com
 */
public class TimeUtils {
    /**
     * 年-月-日
     */
    public static final String DATE2Y = "yy-MM-dd";
    /**
     * 年/月/日
     */
    public static final String DATE4Y2M2D="yyyy/MM/dd";
    /**
     * 年-月-日 时：分
     */
    public static final String DATE2Y_TIME = "yy-MM-dd HH:mm";

    /**
     * 月-日 时：分
     */
    public static final String DATE_MONTH_TIME = "MM-dd HH:mm";

    /**
     * 时 ： 分
     */
    public static final String TIEM_HOUR_MINUTE = "HH:mm";

    /**
     * 年-月-日
     */
    public static final String DATE4Y = "yyyy-MM-dd";
    /**
     * 年.月.日
     */
    public static final String DATE4Y_POINT="yyyy.MM.dd";
    /**
     * 月.日
     */
    public static final String DATE2M2D_POINT="MM.dd";
    /**
     * 月-日
     */
    public static final String DATE2M2D_LINE="MM-dd";
    /**
     * 年-月-日 时：分
     */
    public static final String DATE4Y_TIME = "yyyy-MM-dd HH:mm";
    /**
     * 年.月.日 时:分
     */
    public static final String DATE4Y_POINTTIME_LINE="yyyy.MM.dd HH:mm";
    /**
     * 年-月-日 汉字格式
     */
    public static final String DATE4Y_TIME_CHINESE="yyyy年MM月dd日";
    /**
     * 年-月-日 时：分：秒
     */
    public static final String DATE4Y_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时：分
     */
    public static final String DATE_TIME_2H2M = "HH:mm";
    /**
     * 时：分：秒
     */
    public static final String DATE_TIME_2H2M2S = "HH:mm:ss";

    /**
     *
     *@方法名称:getDate2Y
     *@描述: 得到 yy-MM-dd格式的日期时间字符串
     *@创建人：lipengbo
     *@创建时间：2015年1月15日 上午10:20:38
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getDate2Y(long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE2Y);
        return format.format(new Date(time));
    }

    /**
     *得到 yyyy-MM-dd HH:mm:ss 格式的日期时间字符串
     * @param time
     * @return
     */
    public static String getDate4YTimeSecond(long time){
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_TIME_SECOND);
        return format.format(new Date(time));
    }

    /**
     * 得到 yy-MM-dd HH:mm 格式的日期时间字符串
     *
     * @author km
     * @time 2014年5月9日 下午2:25:18
     * @param time
     * @return
     * @postscript
     */
    public static String getDate2YTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE2Y_TIME);
        return format.format(new Date(time));
    }





    /**
     *
     *@方法名称:getDate4YTime
     *@描述: 得到 yyyy-MM-dd HH:mm 格式的时间字符串
     *@创建人：lipegnbo
     *@创建时间：2014年11月13日 下午3:26:57
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getDate4YTime(long time){
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_TIME);
        return format.format(new Date(time));
    }



    /**
     *
     *@方法名称:getDate4YPointTime
     *@描述:  得到 yyy.MM.dd  格式的日期时间字符串
     *@创建人：lipengbo
     *@创建时间：2014年11月7日 下午3:24:20
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getDate4YPointTime(long time){
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_POINT);
        return format.format(new Date(time));
    }

    /**
     *
     *@方法名称:getDate2M2DPointTime
     *@描述:  得到 MM.dd  格式的日期时间字符串
     *@创建人：李朋波
     *@创建时间：2014年11月7日 下午3:26:20
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getDate2M2DPointTime(long time){
        SimpleDateFormat format = new SimpleDateFormat(DATE2M2D_POINT);
        return format.format(new Date(time));
    }
    /**
     *
     *@方法名称:getDate2M2DLineTime
     *@描述: 得到 MM-dd  格式的日期时间字符串
     *@创建人：李朋波
     *@创建时间：2015年1月15日 上午10:19:00
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getDate2M2DLineTime(long time){
        SimpleDateFormat format = new SimpleDateFormat(DATE2M2D_LINE);
        return format.format(new Date(time));
    }

    /**
     * 得到 HH:mm 格式的时间字符串
     *
     * @author km
     * @time 2014年5月9日 下午2:27:04
     * @param time
     * @return
     * @postscript
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat(TIEM_HOUR_MINUTE);
        return format.format(new Date(time));
    }

    /**
     * Get the time to chat
     */
    public static String getChatTime(long timesamp) {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        SimpleDateFormat sdfYYYY=new SimpleDateFormat("yyyy");
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        int yearTemp=Integer.parseInt(sdfYYYY.format(today)) - Integer.parseInt(sdfYYYY.format(otherDay));
        if(yearTemp==0){
            switch (temp) {
                case 0:
                    result = "今天 " + getHourAndMin(timesamp);
                    break;
                case 1:
                    result = "昨天 " + getHourAndMin(timesamp);
                    break;
                case 2:
                    result = "前天 " + getHourAndMin(timesamp);
                    break;

                default:
                    // result = temp + "天前 ";
                    result = getDate2YTime(timesamp);
                    break;
            }
        }else{
            result = getDate2YTime(timesamp);
        }
        return result;
    }


    /**
     *
     *@方法名称:tips2time
     *@描述: 设置发布时间显示方式
     *@创建人：lipengbo
     *@创建时间：2014年11月13日 下午3:22:48
     *@param @param oldtime
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String publishTime(String oldtime) { // 2014-03-09 12:00:00
        if(oldtime == null || oldtime.indexOf("-") == -1 || oldtime.indexOf(":") == -1)
            return "";
        int[] oldarr = gettimearr(oldtime);
        Date oldDate = new Date(oldarr[0] - 1900, oldarr[1] - 1, oldarr[2],oldarr[3], oldarr[4]);
        long time = oldDate.getTime();
        return getPublishTime(time);
    }

    /**
     *
     *@方法名称:getPublishTime
     *@描述: 获取发布时间
     *@创建人：lipengbo
     *@创建时间：2014年11月13日 下午3:24:14
     *@param @param timesamp
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getPublishTime(long timesamp){
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMM=new SimpleDateFormat("MM");
        SimpleDateFormat sdfYYYY=new SimpleDateFormat("yyyy");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        int tempMM=Integer.parseInt(sdfMM.format(today)) - Integer.parseInt(sdfMM.format(otherDay));
        int yearTemp=Integer.parseInt(sdfYYYY.format(today)) - Integer.parseInt(sdfYYYY.format(otherDay));
        if(yearTemp==0&&tempMM==0){
            switch (temp) {
                case 0:
                    result = "今天 " + getHourAndMin(timesamp);
                    break;
                case 1:
                    result = "昨天 " + getHourAndMin(timesamp);
                    break;
                case 2:
                    result = "前天 " + getHourAndMin(timesamp);
                    break;

                default:
                    // result = temp + "天前 ";
                    result = getDate4YTime(timesamp);
                    break;
            }
        }else{
            result = getDate4YTime(timesamp);
        }
        return result;
    }




    /**
     *
     *@方法名称:getFormatPublishTime
     *@描述: get the time for format publish
     *@创建人：jwl
     *@创建时间：2014年11月7日 下午3:18:45
     *@param @param timesamp
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getFormatPublishTime(String  formatTime){
        if(StringUtils.empty(formatTime)){
            return "";
        }
        int[] timearr=gettimearr(formatTime);
        Date formatDate=new Date(timearr[0] - 1900, timearr[1] - 1, timearr[2],timearr[3], timearr[4]);
        long timesamp = formatDate.getTime();

        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0://在同一年
                result=getDate2M2DPointTime(timesamp);
                break;
            default:
                // result = temp + "天前 ";
                result = getDate4YPointTime(timesamp);
                break;
        }
        return result;
    }

    public static int[] gettimearr(String oldtime) {// 2014-03-09 12:00:00
        // System.out.println("oldtime=============" + oldtime);
        String[] oldtime1 = oldtime.split("\\ ");
        String[] oldtime2 = oldtime1[0].split("\\-");
        String[] oldtime3 = oldtime1[1].split("\\:"); // 2014-3-12下午4:48:00
        int[] i = new int[5];
        i[0] = Integer.parseInt(oldtime2[0]);
        i[1] = Integer.parseInt(oldtime2[1]);
        i[2] = Integer.parseInt(oldtime2[2]);
        String string = oldtime3[0];
        if (string.contains("下午")) {
            String substring = string.substring(string.indexOf("下午")
                    + "下午".length());
            i[3] = Integer.parseInt(substring) + 12;
        } else if (string.contains("上午")) {
            String substring = string.substring(string.indexOf("上午")
                    + "上午".length());
            i[3] = Integer.parseInt(substring);
        } else {
            i[3] = Integer.parseInt(string);
        }
        i[4] = Integer.parseInt(oldtime3[1]);
        return i;
    }



    /**
     *
     *@方法名称:getFormatMessageTime
     *@描述: 获取指定的消息时间格式
     *@创建人：jwl
     *@创建时间：2015年1月15日 上午10:03:29
     *@param @param time
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getFormatMessageTime(String formatTime){
        try{
            int[] timearr=gettimearr(formatTime);
            Date formatDate=new Date(timearr[0] - 1900, timearr[1] - 1, timearr[2],timearr[3], timearr[4]);
            long timesamp = formatDate.getTime();
            String result = "";
            SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");//判断是否在今年
            SimpleDateFormat sdfYYMMdd = new SimpleDateFormat("yyyy-MM-dd");//判断是否在今年本月当天

            Date today = new Date(System.currentTimeMillis());
            Date otherDay = new Date(timesamp);
            int tempYY = Integer.parseInt(sdfYY.format(today)) - Integer.parseInt(sdfYY.format(otherDay));
            boolean  isYYMMdd=sdfYYMMdd.format(today).equals(sdfYYMMdd.format(otherDay))?true:false;
            if(isYYMMdd){//在今年本月当天
                result=DateToHourString(otherDay);
            }else if(tempYY==0)//在今年
            {
                result=getDate2M2DLineTime(timesamp);
            }else{//非今年
                result=getDate2Y(timesamp);
            }
            return result;
        }catch(Exception e){
            return "";
        }
    }

    /**
     * 获取定制时间的格式
     *
     * @param standardTime
     * @return
     */
    public static String getCustomizedTime(String standardTime) {
        Date now = new Date();
        Date chatTime = StringToDate(standardTime);
        long minuties = (now.getTime() - chatTime.getTime()) / 60000; // 当前时间和发帖时间的间隔分钟

        if (minuties < 60) {
            return minuties + "分钟前";
        } else if (minuties < 1440 && minuties >= 60) {
            return minuties / 60 + "小时前";
        } else if (minuties > 1439 && minuties < 1440 * 2) {
            return "昨天";
        } else if (minuties < 1440 * 7 && minuties > 1440 * 2) {
            int day = (int) (minuties / 1440);
            return day + "天前";
        } else if (minuties >= 1440 * 7) {
            return DateToYMDString(chatTime);
        } else {
            return standardTime;
        }
    }

    /**
     * 判断时间是否在一个星期以内
     * @param standardTime
     * @return
     */
    public static boolean getTimeInWeek(String standardTime){
        Date now = new Date();
        Date chatTime = StringToDate4StrikeTime(standardTime);
        long minutes=(now.getTime()-chatTime.getTime())/60000;//当前时间和发帖时间的间隔分钟
        if(minutes<=1440*7)
            return true;
        else
            return false;
    }

    /**
     * 日期转换成 日期时间格式为 MM-dd HH:mm 的字符串
     *
     * @param date
     * @return
     */
    public static String DateToMonthString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_MONTH_TIME);
        return sdf.format(date);
    }

    /**
     * 日期转换成 时间格式为 HH:mm 的字符串
     *
     * @param date
     * @return
     */
    public static String DateToHourString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIEM_HOUR_MINUTE);
        return sdf.format(date);
    }

    /**
     * 字符串转换成日期 格式为 yyyy-MM-dd HH:mm
     *
     * @param str
     * @return
     */
    public static Date StringToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_TIME);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期 格式为 yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static Date StringTo4YDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }








    /**
     * 时间  4Y2M2D
     * @param str
     * @return
     */
    public static String getDate4Y2M2D(String str){
        SimpleDateFormat format1 = new SimpleDateFormat(DATE4Y_TIME_SECOND);
        SimpleDateFormat format2 = new SimpleDateFormat(DATE4Y2M2D);

        Date date = null;
        String strDateValue="";
        try {
            if(str!=null) {
                date = format1.parse(str);
                strDateValue = format2.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDateValue;
    }

    /**
     *
     *@方法名称:StringToDate4StrikeTime
     *@描述:字符串转换成日期 格式为 yyyy-MM-dd HH:mm:ss
     *@创建人：lipengbo
     *@创建时间：2015年3月17日 下午2:59:34
     *@param @param str
     *@param @return
     *@返回类型：Date
     *@throws
     */
    public static Date StringToDate4StrikeTime(String str){
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_TIME_SECOND);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *时间格式转换 yyyy年MM月dd日 转换为 yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     */
    public static String StringToString4StrikeTime(String str){
        SimpleDateFormat format = new SimpleDateFormat(DATE4Y_TIME_CHINESE);
        SimpleDateFormat formatNormal = new SimpleDateFormat(DATE4Y_TIME_SECOND);
        Date date = null;
        try {
            date = format.parse(str);
            return formatNormal.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     *
     *@方法名称:DateToStringDATEPOINTLINE
     *@描述: 转换字符串时间格式为：yyyy.MM.dd HH-ss
     *@创建人：lipengbo
     *@创建时间：2015年3月23日 上午11:28:59
     *@param @param date
     *@param @return
     *@返回类型：String
     *@throws
     */
    public  static String getDateToStringDATEPOINTLINE(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y_POINTTIME_LINE);
        return sdf.format(date);
    }

    /**
     * 日期转换成日期格式为 yyyy-MM-dd 的字符串
     *
     * @param date
     * @return
     */
    public static String DateToYMDString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y);
        return sdf.format(date);
    }

    public static int getDateWeek(String strDate) {
        // String subTime = strDate.substring(0, 10);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y,
                Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.get
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }

    /**
     * 截取 时间格式为  2013-03-26T10:00:00 中的日期
     * @author km
     * @time 2014年5月9日 下午2:49:50
     * @param time
     * @return 如果time 不等于null ,返回 日期格式  2013-03-26
     * @postscript
     */
    public static String removeTail(String time) {
        if (time == null) {
            return null;
        }
        time = time.substring(0, time.indexOf('T'));
        return time;
    }

    /**
     * 替换掉时间格式为  2013-03-26T10:00:00  中的 T 字符
     * @author km
     * @time 2014年5月9日 下午2:44:47
     * @param time
     * @return 如果time不等于 null，返回格式为 2013-03-26 10:00:00
     * @postscript
     */
    public static String removeT(String time) {
        if (time == null) {
            return null;
        }
        time = time.replace('T', ' ');
        return time;
    }

    /**
     * 得到系统当前时间，时间格式为  yyyy-MM-dd HH:mm:ss
     * @author km
     * @time 2014年5月9日 下午2:39:12
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y_TIME_SECOND, Locale.CHINA);
        String time = sdf.format(new Date());
        return time;
    }

    /**
     *
     *@方法名称:isNight
     *@描述: 判断当前时间是否是晚上
     *@创建人：lipengbo
     *@创建时间：2014年9月19日 上午10:18:54
     *@param @return
     *@返回类型：boolean
     *@throws
     */
    public static boolean isNight()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y_TIME_SECOND, Locale.CHINA);
        String time = sdf.format(new Date());
        String[] strDateTime = time.split(" ");
        if (strDateTime != null && strDateTime.length > 1)
        {
            String[] strTime = strDateTime[1].split(":");
            if (Integer.parseInt(strTime[0]) >= 20)
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否为合法的日期时间字符串
     * @param str_input
     * @param str_input
     * @return boolean;符合为true,不符合为false
     */
    public static  boolean isDate(String str_input,String rDateFormat){
        if (!StringUtils.empty(str_input)) {
            SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
            formatter.setLenient(false);
            try {
                formatter.format(formatter.parse(str_input));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }


    public static long getTimeInMillis(String date, String dateFormat)
    {
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(new SimpleDateFormat(dateFormat).parse(date));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return calendar.getTimeInMillis();
    }

    public static String getMillisToTime(long millis, String dateFormat)
    {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(date);
    }


    public static String getAgeToYear(int age, String dateFormat)
    {
        long currentMillis = (System.currentTimeMillis() / (60000L * 60L * 24L)) * (60000L * 60L * 24L);
        String ageDate = TimeUtils.getMillisToTime(currentMillis - ((60000L * 60L * 24L * 365L) * (long)age ), TimeUtils.DATE4Y_POINTTIME_LINE);

        return ageDate;
    }


    /**
     *@方法名称:getCurrentDate
     *@描述: 获取当前时间
     *@创建人：jwl
     *@创建时间：2014年7月28日 下午2:32:55
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getCurrentDate()
    {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE2Y);
        String day = sdf.format(currentDate);
        return day;
    }


    public static String getCurrent4YDate(){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE4Y);
        String day = sdf.format(currentDate);
        return day;
    }




    /**
     *
     *@方法名称:getCurrentDate
     *@描述: 获取当前时间
     *@创建人：jwl
     *@创建时间：2014年7月28日 下午2:32:55
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getCurrentTime2H2M()
    {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_2H2M);
        return sdf.format(currentDate);
    }

    /**
     *
     *@方法名称:getCurrentTimeSS
     *@描述: 方法描述
     *@创建人：jwl
     *@创建时间：2015年1月8日 上午11:35:45
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getCurrentTimeSS()
    {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_2H2M2S);
        return sdf.format(currentDate);
    }

    /**
     *
     *@方法名称:getFormatTimeDistanceOfMinute
     *@描述: 得到时间差距的分钟数字
     *@创建人：lipengbo
     *@创建时间：2014年11月22日 上午11:09:46
     *@param @param formatTimeStart
     *@param @param formatTimeEnd
     *@param @return
     *@返回类型：long
     *@throws
     */
    public static long getFormatTimeDistanceOfMinute(String formatTimeStart,String formatTimeEnd){
        if(StringUtils.empty(formatTimeStart)||StringUtils.empty(formatTimeEnd)){
            return 0;
        }
        int[] timearrStart=gettimearr(formatTimeStart);//开始时间
        int[] timearrEnd=gettimearr(formatTimeEnd);//结束时间
        Date formatDateStart=new Date(timearrStart[0] - 1900, timearrStart[1] - 1, timearrStart[2],timearrStart[3], timearrStart[4]);
        Date formatDateEnd=new Date(timearrEnd[0] - 1900, timearrEnd[1] - 1, timearrEnd[2],timearrEnd[3], timearrEnd[4]);
        long timeStart=formatDateStart.getTime();
        long timeEnd=formatDateEnd.getTime();
        long timeDayDistance=timeEnd-timeStart;
        long day=timeDayDistance/(60*1000);
        return day;
    }

    /**
     * 得到时间差距的天数数字
     * @param formatTimeStart
     * @return
     */
    public static long getFormatTimeDistanceOfDay(String formatTimeStart){
        if(StringUtils.empty(formatTimeStart)){
            return 0;
        }
        Date timeDateStart=StringTo4YDate(formatTimeStart);
        Date timeDateCurrent=new Date(System.currentTimeMillis());
        if(timeDateStart!=null&&timeDateCurrent!=null){
            long timeStart=timeDateStart.getTime();
            long timeEnd=timeDateCurrent.getTime();
            long timeDayDistance=timeEnd-timeStart;
            long day=timeDayDistance/(24*60*60*1000);
            return day;
        }
        return 0;
    }
}
