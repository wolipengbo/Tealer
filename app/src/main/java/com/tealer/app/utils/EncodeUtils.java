package com.tealer.app.utils;

import android.text.TextUtils;

/**
 * Author：pengbo on 2016/3/15 22:53
 * Email：1162947801@qq.com
 */
public class EncodeUtils {
    // 清理UTF-8编码隐藏字符
    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }
}
