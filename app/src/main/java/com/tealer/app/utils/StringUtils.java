package com.tealer.app.utils;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Author：pengbo on 2016/3/20 18:24
 * Email：1162947801@qq.com
 */
public class StringUtils {

    public static Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");


    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    虚拟运营商：17
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
    private static String telRegex="[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，

    public static boolean isMobileNO(String mobiles){
        if(empty(mobiles)){
            return false;
        }
        return mobiles.matches(telRegex);
    }


    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim())
                || "null".equalsIgnoreCase(o.toString().trim())
                || "undefined".equalsIgnoreCase(o.toString().trim());
    }

    /**
     *
     * @方法名称:split
     * @描述: 字符串分割函数
     * @创建人：lipengbo
     * @创建时间：2014年10月15日 下午5:46:42
     * @param @param str 字符串
     * @param @param strSplit 字符串分割符
     * @param @param isRemoveEmpty 是否去除空元素
     * @param @return
     * @返回类型：String[] 字符串数组
     * @throws
     */
    public static ArrayList<String> split(String str, String strSplit,
                                          boolean isRemoveEmpty) {
        String[] strArray;
        ArrayList<String> arrayList;
        if(empty(str)){
            arrayList = new ArrayList<String>();
            arrayList.add("位置信息");
            return arrayList;
        }
        if (!isRemoveEmpty) {
            strArray = str.split(strSplit);
            arrayList = new ArrayList<String>();
            for (int i = 0; i < strArray.length; i++) {
                arrayList.add(strArray[i]);
            }
            return arrayList;
        } else {
            strArray = str.split(strSplit);
            arrayList = new ArrayList<String>();
            for (int i = 0; i < strArray.length; i++) {
                if(!empty(strArray[i])){
                    arrayList.add(strArray[i]);
                }
            }
        }
        return arrayList;
    }


    /**
     * 字符串分割函数
     * @param str
     * @param strSplit
     * @param isRemoveEmpty
     * @param limitLength
     * @return
     */
    public static ArrayList<String> split(String str, String strSplit,
                                          boolean isRemoveEmpty,int limitLength) {
        String[] strArray;
        ArrayList<String> arrayList;
        if (!isRemoveEmpty) {
            strArray = str.split(strSplit,limitLength);
            arrayList = new ArrayList<String>();
            for (int i = 0; i < strArray.length; i++) {
                arrayList.add(strArray[i]);
            }
            return arrayList;
        } else {
            strArray = str.split(strSplit,limitLength);
            arrayList = new ArrayList<String>();
            for (int i = 0; i < strArray.length; i++) {
                if(!empty(strArray[i])){
                    arrayList.add(strArray[i]);
                }
            }
        }
        return arrayList;
    }







    /**
     *
     *@方法名称:connectJoins
     *@描述: 字符数组连接函数
     *@创建人：lipengbo
     *@创建时间：2014年10月16日 上午10:39:40
     *@param @param arrayList
     *@param @param strJoins
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String connectJoins(String preAppend,ArrayList<String> arrayList,char strJoins){
        StringBuilder sb=new StringBuilder();
        sb.append(preAppend);
        for (int i = 0; i < arrayList.size(); i++) {
            sb.append(arrayList.get(i));
            sb.append(strJoins);
        }
        if(sb.toString().lastIndexOf(strJoins)!=-1){
            return sb.toString().substring(0,sb.toString().length()-1);
        }
        return sb.toString();
    }

    /**
     *
     *@方法名称:getFileNameByUrl
     *@描述: 根据Url获取资源的名称
     *@创建人：jwl
     *@创建时间：2014年11月15日 上午11:34:44
     *@param @param url
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getFileNameByUrl(String url){
        if (!url.equals("")) {
            String strChar = "/";
            int startIndex = url
                    .lastIndexOf(strChar) + 1;
            if (startIndex < url
                    .length()) {
                String strFileName =url.substring(
                        startIndex,
                        url
                                .length());

                return strFileName;
            }
        }
        return "";
    }
    /**
     *
     *@方法名称:getFormatText
     *@描述: 得到指定格式的字符串
     *@创建人：lipengbo
     *@创建时间：2014年12月29日 下午4:05:48
     *@param @param numCopy  传入数值
     *@param @param maxNumCopy  最大数值
     *@param @param overFlowChar  溢出数值补充字符
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String getFormatText(String numCopy,String maxNumCopy,String overFlowChar){
        String formatTextValue="";
        try{
            Integer maxNum=Integer.parseInt(maxNumCopy);
            Integer num=Integer.parseInt(numCopy);
            if(num>=maxNum){
                formatTextValue=maxNum+overFlowChar;
                return formatTextValue;
            }else{
                formatTextValue=num+"";
                return  formatTextValue;
            }
        }
        catch(Exception exception){
            return  formatTextValue;
        }
    }


    public static String getRouteString(String sct, String ect)
    {
        String tempSct = sct;
        String tempEct = ect;

        if((tempSct == null || tempSct.length() == 0) && (tempEct == null || tempEct.length() == 0))
            return "";

        if(tempEct == null || tempEct.length() == 0 )
            return tempSct;

        String tempRoute = "";
        if(tempSct == null || tempSct.length() == 0 || tempSct.equals(" "))
            tempRoute = tempEct;
        else
            tempRoute = tempSct + "|" + tempEct;

        ScreenOutput.logI("tempRoute befor" + tempRoute);

        String[] address = tempRoute.split("\\|");
        tempRoute = "";

        if(address != null && address.length > 0)
        {

            if(address[0].indexOf("-") == -1 || address[address.length - 1].indexOf("-") == -1)
            {
                tempRoute = address[0];
                int oldIndex = 0;
                for(int i = 1; i < address.length; ++i)
                {
                    if(!address[oldIndex].equals(address[i]))
                    {
                        oldIndex = i;
                        tempRoute += "-" + address[i];
                    }
                }
            }
            else
            {

                String[] headerAddress = address[0].split("-");
                tempRoute = headerAddress[0];
                String tempAddress = headerAddress[1];

                boolean city = true;
                for(int i = 1; i < address.length; ++i)
                {
                    String[] ads = address[i].split("-");
                    if(ads.length == 2)
                    {
                        if(!headerAddress[0].equals(ads[0]))
                        {
                            city = false;
                            break;
                        }
                        else
                        {
                            tempAddress += "-" + ads[1];
                        }
                    }
                }

                if(!city)
                {
                    String oldCity = tempRoute;
                    for(int i = 1; i < address.length; ++i)
                    {
                        String[] ads = address[i].split("-");
                        if(ads.length == 2)
                        {
                            if( !oldCity.equals(ads[0]) )
                            {
                                oldCity = ads[0];
                                tempRoute += "-" + oldCity;
                            }
                        }
                    }
                }
                else
                {
                    tempRoute += ":" + tempAddress;
                }
            }
        }

        return tempRoute;
    }




    public static String getStartEndTimeString(String st, String et, boolean isList)
    {
        String[] currentTemp = (TimeUtils.getCurrentDate() + " " + TimeUtils.getCurrentTimeSS()).split(" ");
        String[] currentDate = currentTemp[0].split("-");
        String[] currentTime = currentTemp[1].split(":");

        if(et == null || et.length() == 0)
        {
            String[] stTemp = st.split(" ");

            if(stTemp.length == 2 && currentTemp.length == 2)
            {
                String[] stDate = stTemp[0].split("-");
                String[] stTime = stTemp[1].split(":");
                if(stDate.length == 3 && currentDate.length == 3 && stTime.length == 3 && currentTime.length == 3)
                {
                    return ( stDate[0].equals(currentDate[0]) ? "" : stDate[0] + "年" ) + stDate[1] + "月" + stDate[2]  + "日" + ( !isList || stDate[0].equals(currentDate[0]) ? " " + stTime[0] + ":" + stTime[1] : "" );
                }
            }
        }
        else
        {
            String[] stTemp = st.split(" ");
            String[] etTemp = et.split(" ");

            if(stTemp.length == 2 && etTemp.length == 2)
            {
                String[] stDate = stTemp[0].split("-");
                String[] etDate = etTemp[0].split("-");
                String[] stTime = stTemp[1].split(":");
                String[] etTime = etTemp[1].split(":");
                if(stDate.length == 3 && etDate.length == 3 && stTime.length == 3 && etTime.length == 3)
                {
                    if(stTemp[0].equals(etTemp[0]))  // 同一天
                    {
                        if(stTime[0].equals(etTime[0]) && stTime[1].equals(etTime[1]) )
                            return (stDate[0].equals(currentDate[0]) ? "" : stDate[0] + "年" ) + stDate[1] + "月" + stDate[2]  + "日" + " " + stTime[0] + ":" + stTime[1];
                        else
                            return (stDate[0].equals(currentDate[0]) ? "" : stDate[0] + "年" ) + stDate[1] + "月" + stDate[2]  + "日" + " " + stTime[0] + ":" + stTime[1] + "-" + etTime[0] + ":" + etTime[1];
                    }
                    else if(stDate[0].equals(etDate[0]))  // 同一年不同一天
                    {
                        return (stDate[0].equals(currentDate[0]) ? "" : stDate[0] + "年" ) + stDate[1] + "月" + stDate[2]  + "日" + (isList ? "" : " " + stTime[0] + ":" + stTime[1])
                                + "-" + (etDate[0].equals(currentDate[0]) ? "" : etDate[0] + "年" ) + etDate[1] + "月" + etDate[2]  + "日" + (isList ? "" : " " + etTime[0] + ":" + etTime[1]);
                    }
                    else
                    {
                        return stDate[0] + "年" + stDate[1] + "月" + stDate[2]  + "日" + (isList ? "" : " " + stTime[0] + ":" + stTime[1])
                                + "-" + etDate[0] + "年" + etDate[1] + "月" + etDate[2]  + "日" + (isList ? "" : " " + etTime[0] + ":" + etTime[1]);
                    }
                }
            }

        }

        return "";
    }



    //	XXX市XXX区
//	XXX省XXX市
//	XXX省XXX市XXX区
//	XXX省XXX市XXX县
//	XXX省XXX市XXX自治县
//	XXX省XXX自治州
//	XXX省XXX自治州XXX市
//	XXX省XXX自治州XXX县
//	XXX自治区XXX市
//	XXX自治区XXX自治州
//	XXX自治区XXX自治州XXX市
//	XXX自治区XXX旗
//	XXX自治区XXX地区XXX县
    public static String filtrationCity(String value)
    {
        if(value.length() > 0 && value != null)
        {
            String city = value;
            int index = 0;
            if( (index = city.indexOf("市") ) > 0 )
            {
                String str1 = city.substring(0, index + 1);
                do
                {
                    if( (index = str1.indexOf("旗") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else if( (index = str1.indexOf("地区") ) > 0 )
                    {
                        index +=2;
                        break;
                    }
                    else if( (index = str1.indexOf("自治州") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("自治区") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);

                city = str1.substring(index, str1.length());
            }
            else if( (index = city.indexOf("特别行政区") ) > 0 )
            {
                String str1 = city.substring(0, index + 5);
                city = str1;
            }
            else if( (index = city.indexOf("旗") ) > 0 )
            {
                String str1 = city.substring(0, index + 1);
                do
                {
                    if( (index = str1.indexOf("自治州") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("地区") ) > 0 )
                    {
                        index +=2;
                        break;
                    }
                    else if( (index = str1.indexOf("自治区") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);
                city = str1.substring(index, str1.length());
            }
            else if( (index = city.indexOf("自治州") ) > 0 )
            {
                String str1 = city.substring(0, index + 3);
                do
                {
                    if( (index = str1.indexOf("旗") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else if( (index = str1.indexOf("地区") ) > 0 )
                    {
                        index +=2;
                        break;
                    }
                    else if( (index = str1.indexOf("自治区") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);
                city = str1.substring(index, str1.length());
            }
            else if( (index = city.indexOf("地区") ) > 0 )
            {
                String str1 = city.substring(0, index + 2);
                do
                {
                    if( (index = str1.indexOf("自治区") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("自治州") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);
                city = str1.substring(index, str1.length());
            }
            else if( (index = city.indexOf("自治区") ) > 0 )
            {
                String str1 = city.substring(0, index + 3);
                do
                {
                    if( (index = str1.indexOf("地区") ) > 0 )
                    {
                        index +=2;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);
                city = str1.substring(index, str1.length());
            }
            else if( (index = city.indexOf("区") ) > 0 )
            {
                String str1 = city.substring(0, index + 1);
                do
                {
                    if( (index = str1.indexOf("地区") ) > 0 )
                    {
                        index +=2;
                        break;
                    }
                    else if( (index = str1.indexOf("自治州") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("自治区") ) > 0 )
                    {
                        index +=3;
                        break;
                    }
                    else if( (index = str1.indexOf("省") ) > 0 )
                    {
                        index +=1;
                        break;
                    }
                    else
                        index = 0;
                }while(false);
                city = str1.substring(index, str1.length());
            }

            return city;
        }
        else
            return "";
    }

    /**
     * 获取电话号码
     * @param strPhone
     * @return
     */
    public static String getRealPhoneText(String strPhone) {
        if(!empty(strPhone)) {
            List<String> listPhone = new ArrayList<>();
            String[] array = strPhone.split("\\n");
            for (int i = 0; i < array.length; i++) {
                String phoneNumber = array[i].replace(" ", "");
                if (phoneNumber.length() > 6) {
                    String[] array1 = phoneNumber.split("、");
                    String[] array2 = phoneNumber.split("；");
                    if (array1.length > 1) {
                        for (int j = 0; j < array1.length; j++) {
                            String phoneNumber1 = array1[i].replace(" ", "");
                            if (phoneNumber1.length() > 6)
                                listPhone.add(phoneNumber1);
                        }
                    } else if (array2.length > 1) {
                        for (int k = 0; k < array2.length; k++) {
                            String phoneNumber2 = array2[i].replace(" ", "");
                            if (phoneNumber2.length() > 6)
                                listPhone.add(phoneNumber2);
                        }
                    } else {
                        listPhone.add(phoneNumber);
                    }
                }
            }
            return listPhone.size() > 0 ? listPhone.get(0) : "";
        }else
            return "";
    }

    public static Paint getTextPaint(float size) {
        Paint mTextPaint = new Paint();
        Paint.Style style = mTextPaint.getStyle();
        mTextPaint.setStyle(style);
        mTextPaint.setTextSize(size); // 指定字体大小

        return mTextPaint;
    }
}
