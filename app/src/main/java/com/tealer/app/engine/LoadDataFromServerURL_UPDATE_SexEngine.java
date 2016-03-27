package com.tealer.app.engine;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.tealer.app.Constant;
import com.tealer.app.http.HttpConfig;
import com.tealer.app.http.HttpManagerDetail;
import com.tealer.app.http.HttpManagerPost;
import com.tealer.app.utils.CompressImageUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.Map;

/**
 * Author：pengbo on 2016/3/27 22:46
 * Email：1162947801@qq.com
 */
public class LoadDataFromServerURL_UPDATE_SexEngine {
    public static <T> HttpManagerDetail getResult(Callback.CommonCallback<T> callback, Map<String, Object> params, Context context)
    {
        RequestParams rp = new RequestParams(Constant.URL_SOCIAL_FRIEND);
        rp.addQueryStringParameter("hxid", params.get("hxid").toString());
        rp.addQueryStringParameter("sex", params.get("sex").toString());
        return HttpManagerPost.send(HttpConfig.TIMEOUT_DEFAULT_CONN, callback, rp, context);
    }


    public static JSONObject parseResult(String strJSON, Context context)
    {
        if (BaseEngine.parseBaseResult(strJSON))
            return JSONObject.parseObject(strJSON);
        return null;
    }
}
