package com.tealer.app.engine;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.tealer.app.Constant;
import com.tealer.app.http.HttpConfig;
import com.tealer.app.http.HttpManagerDetail;
import com.tealer.app.http.HttpManagerPost;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.Map;

/**
 * Author：pengbo on 2016/3/27 16:03
 * Email：1162947801@qq.com
 */
public class SocialApiTaskURL_SOCIALEngine {
    public static <T> HttpManagerDetail getResult(Callback.CommonCallback<T> callback, Map<String, Object> params, Context context)
    {
        RequestParams rp = new RequestParams(Constant.URL_SOCIAL);
        rp.addQueryStringParameter("userID", params.get("userID").toString());
        rp.addQueryStringParameter("num", params.get("num").toString());
        return HttpManagerPost.send(HttpConfig.TIMEOUT_DEFAULT_CONN, callback, rp, context);
    }


    public static JSONObject parseResult(String strJSON, Context context)
    {
        if (BaseEngine.parseBaseResult(strJSON))
                return JSONObject.parseObject(strJSON);
        return null;
    }
}
