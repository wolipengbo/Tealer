package com.tealer.app.engine;

import android.content.Context;

import com.tealer.app.Constant;
import com.tealer.app.http.HttpConfig;
import com.tealer.app.http.HttpManagerDetail;
import com.tealer.app.http.HttpManagerPost;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.Map;

/**
 * @author lipengbo
 * @date 2016/3/18
 * @intro 简介
 */
public class GetFriendsListEngine  {
    /**
     * 简介：登录
     * 作者：jwliu
     * 时间：on 2015/6/11 13:42
     * 邮箱：liujiangwei@triphare.com
     */
    public static <T> HttpManagerDetail getResult(Callback.CommonCallback<T> callback, Map<String, Object> params, Context context)
    {
        RequestParams rp = new RequestParams(Constant.URL_FriendList);

        rp.addQueryStringParameter("hxid", params.get("hxid").toString());
        return HttpManagerPost.send(HttpConfig.TIMEOUT_DEFAULT_CONN, callback, rp, context);
    }


    public static JSONObject parseResult(String strJSON, Context context)
    {
        if (BaseEngine.parseBaseResult(strJSON))
        {
            try {
                return new JSONObject(strJSON);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
