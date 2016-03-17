package com.tealer.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：pengbo on 2016/3/13 13:54
 * Email：1162947801@qq.com
 */
public class TealerApplication  extends Application{
    public static Context applicationContext;
    private static TealerApplication instance;
    private List<Activity> aList = new ArrayList<Activity>();
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

        HXSDKHelper.getInstance().init(applicationContext);
    }

    public static TealerApplication getInstance() {
        return instance;
    }
    public void addActivity(Activity activity) {
        if (!aList.contains(activity)) {
            aList.add(activity);
        }
    }

    public void finishActivitys() {

        for (int i = 0; i < aList.size(); i++) {

            if (!aList.get(i).isFinishing())
                aList.get(i).finish();

        }
    }
}
