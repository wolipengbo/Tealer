package com.tealer.app.http;

import android.content.pm.LauncherApps;

import org.xutils.common.Callback;

/**
 * Author：pengbo on 2016/3/15 22:30
 * Email：1162947801@qq.com
 */
public class HttpRequestCallBack  implements Callback.ProgressCallback<String> {

    public HttpRequestCallBack(){

    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
