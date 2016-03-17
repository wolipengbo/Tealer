package com.tealer.app.http;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

/**
 * Author：pengbo on 2016/3/15 22:32
 * Email：1162947801@qq.com
 */
public class HttpManagerDetail {
    /**
     * code
     */
    private int httpCode = 0;

    /**
     * 网络类型
     */
    private int netWorkType = 0;

    /**
     * http handler
     */
    private Callback.Cancelable cancelable = null;

    /**
     * RequestParams
     */
    private RequestParams requestParams = null;


    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(int netWorkType) {
        this.netWorkType = netWorkType;
    }

    public Callback.Cancelable getCancelable() {
        return cancelable;
    }

    public void setCancelable(Callback.Cancelable cancelable) {
        this.cancelable = cancelable;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }
}
