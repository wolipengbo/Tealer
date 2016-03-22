package com.tealer.app.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * @author lipengbo
 * @date 2016/3/22
 * @intro 图片下载类
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper instance;

    private ImageLoaderHelper( )
    {
        // TODO Auto-generated constructor stub
    }

    public static ImageLoaderHelper GetInstance()
    {
        //双重校验锁
        if (instance == null) {
            synchronized (ImageLoaderHelper.class) {
                if (instance == null) {
                    instance = new ImageLoaderHelper();
                }
            }
        }
        return instance;
    }


    public void display(View container, String uri, ImageOptions displayConfig )
    {

        x.image().bind((ImageView) container, uri, displayConfig);
    }

    public void display(View container, String uri, ImageOptions displayConfig, Callback.ProgressCallback<Drawable> callBack )
    {

        x.image().bind((ImageView) container, uri, displayConfig, callBack);
    }


    public void clearCacheFiles()
    {
        x.image().clearCacheFiles();
    }

    public void clearMemCache()
    {
        x.image().clearMemCache();
    }

    public void clearDoubleCache()
    {
        x.image().clearMemCache();
        x.image().clearCacheFiles();
    }
}
