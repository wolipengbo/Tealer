package com.tealer.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author：pengbo on 2016/3/15 22:45
 * Email：1162947801@qq.com
 */
public class BaseEngine {
    public static boolean parseBaseResult(String strJSON)
    {
        try
        {
            JSONObject jso = new JSONObject(strJSON);

            return jso!=null;
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
