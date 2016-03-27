package com.tealer.app.utils;

import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;


import com.tealer.app.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

/**
 * @author lipengbo
 * @date 2016/3/22
 * @intro 简介
 */
public class BitmapDisplayConfigHelper {
    private static BitmapDisplayConfigHelper instance = new BitmapDisplayConfigHelper();

    private ImageOptions mIconBitmapUtilsConfig = null;
    private ImageOptions mRectIconBitmapUtilsConfig = null;
    private ImageOptions mSmallBitmapUtilsConfig = null;
    private ImageOptions mBigBitmapUtilsConfig = null;
    private ImageOptions mLocalBitmapUtilsConfig = null;
    private ImageOptions mMovieBitmapUtilsConfig = null;   //电影
    private ImageOptions mShowImageDetailUtilsConfig = null;

    private ImageOptions mCircularImageUtilsConfig = null;
    private ImageOptions mArcBitmapUtilsConfig = null;

    private AlphaAnimation animation = null;

    private BitmapDisplayConfigHelper()
    {
        animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(500);
    }

    public static BitmapDisplayConfigHelper GetInstance()
    {
        return instance;
    }


    /**
     *@方法名称:getIconBitmapUtilsConfig
     *@描述: 方法描述
     *@创建人：jwl
     *@创建时间：2015年3月2日 下午1:25:14
     *@param @param context
     *@param @return
     *@返回类型：BitmapDisplayConfig
     *@throws
     */
    public ImageOptions getIconBitmapUtilsConfig()
    {
        if(mIconBitmapUtilsConfig == null)
        {

            mIconBitmapUtilsConfig = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                    .setRadius(DensityUtil.dip2px(5))
                    .setCrop(true)  // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP) // 加载中或错误图片的ScaleType
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.ic_person)
                    .setFailureDrawableId(R.drawable.ic_person)
                    .setAnimation(animation)
                    .build();

        }
        return mIconBitmapUtilsConfig;
    }



    public ImageOptions getRectIconBitmapUtilsConfig()
    {
        if(mRectIconBitmapUtilsConfig == null)
        {

            mRectIconBitmapUtilsConfig = new ImageOptions.Builder()
                    .setSize((int) (DeviceInforUtils.widthScreen / 2), (int) (DeviceInforUtils.widthScreen / 2))
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                            //.setCrop(true)
                            // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.ic_person)
                    .setFailureDrawableId(R.drawable.ic_person)
                    .setAnimation(animation)
                    .build();

        }
        return mRectIconBitmapUtilsConfig;
    }

    /**
     *
     *@方法名称:getSmallBitmapUtilsConfig
     *@描述: 方法描述
     *@创建人：jwl
     *@创建时间：2015年3月2日 下午1:25:20
     *@param @param context
     *@param @return
     *@返回类型：BitmapDisplayConfig
     *@throws
     */
    public ImageOptions getSmallBitmapUtilsConfig()
    {
        if(mSmallBitmapUtilsConfig == null)
        {
            mSmallBitmapUtilsConfig = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(256), DensityUtil.dip2px(256))
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                            // 加载中或错误图片的ScaleType
                    .setConfig(Bitmap.Config.ARGB_4444)
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.default_rect_image)
                    .setFailureDrawableId(R.drawable.default_rect_image)
                    .setAnimation(animation)
                    .build();
        }
        return mSmallBitmapUtilsConfig;
    }

    /**
     *
     *@方法名称:getBigBitmapUtilsConfig
     *@描述: 方法描述
     *@创建人：jwl
     *@创建时间：2015年3月2日 下午1:25:25
     *@param @param context
     *@param @return
     *@返回类型：BitmapDisplayConfig
     *@throws
     */
    public ImageOptions getBigBitmapUtilsConfig()
    {
        if(mBigBitmapUtilsConfig == null)
        {
            mBigBitmapUtilsConfig = new ImageOptions.Builder()
                    .setSize((int) DeviceInforUtils.widthScreen, (int) DeviceInforUtils.widthScreen)
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                            //.setCrop(true)
                            // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.local_default_image)
                    .setFailureDrawableId(R.drawable.default_image)
                    .setAnimation(animation)
                    .build();

        }

        return mBigBitmapUtilsConfig;
    }

    public ImageOptions getShowImageDetailUtilsConfig()
    {
        if(mShowImageDetailUtilsConfig == null)
        {
            mShowImageDetailUtilsConfig = new ImageOptions.Builder()
                    .setSize((int) DeviceInforUtils.widthScreen, (int) DeviceInforUtils.widthScreen)
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                            //.setCrop(true)
                            // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setLoadingDrawableId(R.drawable.local_default_image)
                    .setFailureDrawableId(R.drawable.local_default_image)
                    .setAnimation(animation)
                    .build();
        }
        return mShowImageDetailUtilsConfig;
    }

    /**
     *
     *@方法名称:getLocalBitmapUtilsConfig
     *@描述: 方法描述
     *@创建人：jwl
     *@创建时间：2015年3月12日 上午11:39:21
     *@param @return
     *@返回类型：BitmapDisplayConfig
     *@throws
     */
    public ImageOptions getLocalBitmapUtilsConfig()
    {
        if(mLocalBitmapUtilsConfig == null)
        {
            mLocalBitmapUtilsConfig = new ImageOptions.Builder()
                    .setSize((int) DeviceInforUtils.widthScreen, (int) DeviceInforUtils.widthScreen)
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                            //.setCrop(true)
                            // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.local_default_image)
                    .setFailureDrawableId(R.drawable.local_default_image)
                    .setAnimation(animation)
                    .build();

        }
        return mLocalBitmapUtilsConfig;
    }



    public ImageOptions getCircularImageUtilsConfig()
    {
        if(mCircularImageUtilsConfig == null)
        {

            mCircularImageUtilsConfig = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(128), DensityUtil.dip2px(128))
                    .setCircular(true)
                    .setCrop(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.drawable.default_rect_image)
                    .setAnimation(animation)
                    .build();
        }
        return mCircularImageUtilsConfig;
    }
}
