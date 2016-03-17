package com.tealer.app.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.tealer.app.TealerApplication;

import org.xutils.x;

/**
 * Author：pengbo on 2016/3/13 14:26
 * Email：1162947801@qq.com
 */
public class BaseActivity extends FragmentActivity {
    protected NotificationManager notificationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        x.view().inject(this);
        TealerApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示


    }

}
