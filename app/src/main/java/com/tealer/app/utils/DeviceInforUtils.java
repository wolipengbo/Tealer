package com.tealer.app.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * @author lipengbo
 * @date 2016/3/22
 * @intro 简介
 */
public class DeviceInforUtils {
    public static float widthScreen = 0.0f;
    public static float heightScreen = 0.0f;
    public static float densityScreen = 0.0f;
    public static float statusBarHeight = 0.0f;
    public static float navigationBarHeight = 0.0f;

    /**
     *
     *@方法名称:getDisplayMetrics
     *@描述: 获取设备屏幕信息
     *@创建人：jwl
     *@创建时间：2014年7月28日 下午2:22:41
     *@param @param context
     *@返回类型：void
     *@throws
     */
    public static void getDisplayMetrics(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;
        densityScreen = dm.density;

        statusBarHeight = getStatusBarHeight(context);
        navigationBarHeight = getNavigationBarHeight(context);

    }

    /**
     *
     *@方法名称:getStatusBarHeight
     *@描述: 获取状态栏高度
     *@创建人：jwl
     *@创建时间：2014年7月28日 下午2:25:29
     *@param @param context
     *@param @return
     *@返回类型：int
     *@throws
     */
    public static int getStatusBarHeight(Context context)
    {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try
        {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     *
     *@方法名称:getNavigationBarHeight
     *@描述: 获取NavigationBar高度
     *@创建人：jwl
     *@创建时间：2014年11月19日 下午3:33:25
     *@param @param context
     *@param @return
     *@返回类型：int
     *@throws
     */
    public static int getNavigationBarHeight(Context context)
    {
        Resources resources = context.getResources();
        int navigationBarHeight = 0;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if(rid > 0)
        {
            if (resources.getBoolean(rid))
            {
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0)
                {
                    navigationBarHeight = resources.getDimensionPixelSize(resourceId);  //获取高度
                }
            }
        }

        return navigationBarHeight;
    }

    /**
     *
     * @方法名称:getSDKName
     * @描述: 根据API编号获取Android版本
     * @创建人：jwl
     * @创建时间：2014年7月28日 下午2:10:38
     * @param @param sdkint
     * @param @return
     * @返回类型：String
     * @throws
     */
    public static String getSDKName(int sdkint)
    {
        String name = "";
        switch (sdkint)
        {
            case 3:
                name = "1.5";
                break;
            case 4:
                name = "1.6";
                break;
            case 7:
                name = "2.1";
                break;
            case 8:
                name = "2.0";
                break;
            case 10:
                name = "2.3.3";
                break;
            case 11:
                name = "3.0";
                break;
            case 12:
                name = "3.1";
                break;
            case 13:
                name = "3.2";
                break;
            case 14:
                name = "4.0";
                break;
            case 15:
                name = "4.0.3";
                break;
            case 16:
                name = "4.0.2";
                break;
            case 17:
                name = "4.2";
                break;
            case 18:
                name = "4.3";
                break;
            case 19:
                name = "4.4.2";
                break;
            default:
                name = sdkint + "";
        }
        return name;
    }


    /**
     * 判断App在前台还是在后台
     * @param context
     * @return
     */
    public static boolean appIsBackground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
}
