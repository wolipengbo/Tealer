package com.tealer.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author：pengbo on 2016/3/20 18:26
 * Email：1162947801@qq.com
 */
public class ScreenOutput {
    private static final boolean NORMAL_LOG = true;

    private static final String TAG = "TRIPHARE";


    public static void logI(String string) {
        if (NORMAL_LOG)
            android.util.Log.i(TAG, string);
    }

    public static void logE(String string) {
        if (NORMAL_LOG)
            android.util.Log.e(TAG, string);
    }

    public static void logD(String string) {
        if (NORMAL_LOG)
            android.util.Log.d(TAG, string);
    }

    public static void logW(String string) {
        if (NORMAL_LOG)
            android.util.Log.w(TAG, string);
    }


    /**
     * 长Toast 大概是5秒
     *
     * @param context
     * @param message
     * @author km
     * @time 2014年5月9日 上午11:41:32
     * @postscript
     */
    public static void makeLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void makeLong(Context context, int resid) {
        Toast.makeText(context, context.getResources().getString(resid), Toast.LENGTH_LONG).show();
    }

    /**
     * 短Toast  大概是 2秒
     *
     * @param context
     * @param message
     * @author km
     * @time 2014年5月9日 上午11:41:55
     * @postscript
     */
    public static void makeShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeShort(Context context, int resid) {
        Toast.makeText(context, context.getResources().getString(resid), Toast.LENGTH_SHORT).show();
    }
}
