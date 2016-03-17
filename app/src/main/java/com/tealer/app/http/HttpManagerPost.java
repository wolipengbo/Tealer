package com.tealer.app.http;

import android.content.Context;

import com.tealer.app.utils.NetWorkUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Author：pengbo on 2016/3/15 22:33
 * Email：1162947801@qq.com
 */
public class HttpManagerPost {
    public static final int HTTPCODE_DEFAULT = 0;
    public static final int HTTPCODE_NETWORK = 1;
    public static final int HTTPCODE_PARAMS = 2;
    public static final int HTTPCODE_CALLBACK = 3;

    public static <T> HttpManagerDetail send(int timeOut, Callback.CommonCallback<T> callback, RequestParams mParams, Context context)
    {
        HttpManagerDetail hmd = new HttpManagerDetail();
        hmd.setNetWorkType(NetWorkUtils.getNetWorkType(context));

        if (hmd.getNetWorkType() == NetWorkUtils.NETWORKTYPE_INVALID || !NetWorkUtils.isNetwork(context)) {
            hmd.setHttpCode(HTTPCODE_NETWORK);
            return hmd;
        }

        if (null == callback) {
            hmd.setHttpCode(HTTPCODE_CALLBACK);
            return hmd;
        }

        if (null == mParams){
            hmd.setHttpCode(HTTPCODE_PARAMS);
            return hmd;
        }

        hmd.setHttpCode(HTTPCODE_DEFAULT);

        mParams.setConnectTimeout(timeOut);
        mParams.setMaxRetryCount(0);
//        mParams.addHeader("tk", SharedPreferencesUtils.getString(context, SharedPreferencesUtils.USERTOKEN, ""));
//        mParams.addHeader("id", SharedPreferencesUtils.getString(context, SharedPreferencesUtils.USERGUID, ""));
//        mParams.addHeader("version", "1.6.3");

        hmd.setRequestParams(mParams);

        Callback.Cancelable cancelable = x.http().post(mParams, callback);

        hmd.setCancelable(cancelable);

        return hmd;
    }
}
