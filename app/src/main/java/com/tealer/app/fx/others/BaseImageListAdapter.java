package com.tealer.app.fx.others;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.widget.BaseAdapter;

import org.xutils.common.Callback;

/**
 * @author lipengbo
 * @date 2016/3/22
 * @intro 简介
 */
public abstract class BaseImageListAdapter extends BaseAdapter {

    private LruCache<String, Object> objectCache = null;

    public BaseImageListAdapter()
    {
        this.objectCache = new LruCache<>((int) ((Runtime.getRuntime().maxMemory() / 1024) / 8));
    }

    public synchronized void addObjectToCache(String key, Object obj)
    {
        if (this.objectCache.get(key) == null)
        {
            if (key != null && obj != null)
                this.objectCache.put(key, obj);
        }
    }

    public synchronized Object getObjectByKey(String key)
    {
        Object obj = this.objectCache.get(key);
        if (obj != null && key != null) {
            return obj;
        }
        return null;
    }


    public synchronized Bitmap getBitmapByKey(String key)
    {
        Object obj = this.objectCache.get(key);
        if (obj != null && key != null) {
            try {
                return (Bitmap) obj;
            }catch (Exception e){
                return null;
            }
        }

        return null;
    }


    public void clearCache() {
        if (this.objectCache != null) {
            if (this.objectCache.size() > 0) {
                this.objectCache.evictAll();
            }
            //this.objectCache = null;
        }
    }

    /**
     * 图片加载回调
     */
    public class ImageBitmapLoadCallBack implements Callback.ProgressCallback<Drawable> {

        private String imageId;

        public ImageBitmapLoadCallBack(String iid)
        {
            this.imageId = iid;
        }

//        @Override
//        public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4)
//        {
//            // TODO Auto-generated method stub
//            addObjectToCache(this.imageId, arg2);
//
//            fadeInDisplay((ImageView)arg0, arg2);
//            //((ImageView)arg0).setImageBitmap(arg2);
//
//        }
//
//
//        @Override
//        public void onLoadFailed(View arg0, String arg1, Drawable arg2)
//        {
//            // TODO Auto-generated method stub
//            ((ImageView)arg0).setImageDrawable(arg2);
//        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

        }


        @Override
        public void onSuccess(Drawable result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }

    /**
     * 头像加载回调
     */
    public class IconBitmapLoadCallBack implements Callback.ProgressCallback<Drawable> {

        private String userId;

        public IconBitmapLoadCallBack(String uid)
        {
            this.userId = uid;
        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

        }

        @Override
        public void onSuccess(Drawable result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }

//        @Override
//        public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4)
//        {
//            // TODO Auto-generated method stub
//
//            addObjectToCache(this.userId, arg2);
//            //fadeInDisplay((CircularImageView)arg0, arg2);
//
//            ((CircularImageView)arg0).setImageBitmap(arg2);
//        }
//
//        @Override
//        public void onLoadFailed(View arg0, String arg1, Drawable arg2)
//        {
//            // TODO Auto-generated method stub
//            ((CircularImageView)arg0).setImageDrawable(arg2);
//        }

    }


    public class LimitlessImageViewLoadCallBack implements Callback.ProgressCallback<Drawable>
    {
        private String userId;

        public LimitlessImageViewLoadCallBack(String uid)
        {
            this.userId = uid;
        }

//        @Override
//        public void onLoadCompleted( View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4)
//        {
//            // TODO Auto-generated method stub
//            addObjectToCache(this.userId, arg2);
//
//            ((LimitlessImageView) arg0).setImageBitmap(arg2);
//        }
//
//        @Override
//        public void onLoadFailed(View arg0, String arg1, Drawable arg2)
//        {
//            // TODO Auto-generated method stub
//            ((LimitlessImageView)arg0).setImageDrawable(arg2);
//        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

        }

        @Override
        public void onSuccess(Drawable result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }



//    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
//            android.R.color.transparent);
//    /**
//     * @author sunglasses
//     * @category 图片加载效果
//     * @param imageView
//     * @param bitmap
//     */
//    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {//目前流行的渐变效果
//        final TransitionDrawable transitionDrawable = new TransitionDrawable( new Drawable[]
//                {
//                    TRANSPARENT_DRAWABLE,
//                    new BitmapDrawable(imageView.getResources(), bitmap)
//                });
//        imageView.setImageDrawable(transitionDrawable);
//        transitionDrawable.startTransition(500);
//    }


//    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable>
//    {
//        private RoundProgressBar roundProgressBar;
//        private String imageId;
//
//        public CustomBitmapLoadCallBack(String id, RoundProgressBar progress)
//        {
//            this.imageId = id;
//
//            this.roundProgressBar = progress;
//            this.roundProgressBar.setMax(100);
//            this.roundProgressBar.setProgress(0);
//        }
//
////        @Override
////        public void onLoadStarted(View container, String uri, BitmapDisplayConfig config)
////        {
////            // TODO Auto-generated method stub
////            super.onLoadStarted(container, uri, config);
////            ScreenOutput.logI("onLoadStarted");
////
////            this.roundProgressBar.setVisibility(View.VISIBLE);
////        }
////
////
////        @Override
////        public void onLoading(View container, String uri, BitmapDisplayConfig config, long total, long current)
////        {
////            // TODO Auto-generated method stub
////            super.onLoading(container, uri, config, total, current);
////            ScreenOutput.logI("onLoading" + (int) ( ( (float)current / total ) * 100 ) );
////
////            this.roundProgressBar.setProgress((int) ( ( (float)current / total ) * 100 ) );
////        }
////
////
////        @Override
////        public void onPreLoad(View container, String uri, BitmapDisplayConfig config)
////        {
////            // TODO Auto-generated method stub
////            super.onPreLoad(container, uri, config);
////            ScreenOutput.logI( "onPreLoad");
////        }
////
////        @Override
////        public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4)
////        {
////            // TODO Auto-generated method stub
////            ScreenOutput.logI("onLoadCompleted");
////
////            addObjectToCache(this.imageId, arg2);
////            ((ImageView)arg0).setImageBitmap(arg2);
////
////            this.roundProgressBar.setVisibility(View.GONE);
////        }
////
////        @Override
////        public void onLoadFailed(View arg0, String arg1, Drawable arg2)
////        {
////            // TODO Auto-generated method stub
////            ScreenOutput.logI( "onLoadFailed");
////            ((ImageView)arg0).setImageDrawable(arg2);
////
////            this.roundProgressBar.setVisibility(View.GONE);
////        }
//
//        @Override
//        public void onWaiting() {
//
//        }
//
//        @Override
//        public void onStarted() {
//
//        }
//
//        @Override
//        public void onLoading(long total, long current, boolean isDownloading) {
//
//        }
//
//        @Override
//        public void onSuccess(Drawable result) {
//
//        }
//
//        @Override
//        public void onError(Throwable ex, boolean isOnCallback) {
//
//        }
//
//        @Override
//        public void onCancelled(CancelledException cex) {
//
//        }
//
//        @Override
//        public void onFinished() {
//
//        }
//    }
}
