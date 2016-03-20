package com.tealer.app.engine;

import android.content.Context;

import com.tealer.app.Constant;
import com.tealer.app.http.HttpConfig;
import com.tealer.app.http.HttpManagerDetail;
import com.tealer.app.http.HttpManagerPost;
import com.tealer.app.utils.CompressImageUtils;
import com.tealer.app.utils.StringCodeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.Map;

/**
 * Author：pengbo on 2016/3/20 17:01
 * Email：1162947801@qq.com
 */
public class RegisterTelEngine {

    public static <T> HttpManagerDetail getResult(Callback.CommonCallback<T> callback, Map<String, Object> params, Context context)
    {
        RequestParams rp = new RequestParams(Constant.URL_Register_Tel);
        rp.addQueryStringParameter("usernick", StringCodeUtils.toUTF8(params.get("usernick").toString()));
        rp.addQueryStringParameter("usertel", params.get("usertel").toString());
        rp.addQueryStringParameter("password",params.get("password").toString());
        if(params.containsKey("image"))
        {
            rp.setMultipart(true);

            try
            {
                File file = CompressImageUtils.compressLocalPhoto(context, params.get("image").toString());
                if (file != null && file.exists())
                {
                    rp.addBodyParameter("image", file);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        return HttpManagerPost.send(HttpConfig.TIMEOUT_DEFAULT_CONN, callback, rp, context);
    }



    /**
     *
     *@方法名称:parseResult
     *@描述:  创建位置结果
     *@创建人：jwl
     *@创建时间：2015年9月18日 15:11:53
     *@param @param strJSON
     *@param @param context
     *@param @return
     *@返回类型：String
     *@throws
     */
    public static String parseResult(String strJSON)
    {
        JSONObject jsonObject;
        try
        {
            if (BaseEngine.parseBaseResult(strJSON))
            {
                jsonObject = new JSONObject(strJSON);
                if (jsonObject != null)
                {

                    JSONObject json = jsonObject.getJSONObject("dt");
                    String psid = json.getString("psid");

                    return psid;
                }
            }
            else
            {
                return "";
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
