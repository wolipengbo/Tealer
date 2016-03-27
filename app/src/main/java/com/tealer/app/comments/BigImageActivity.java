package com.tealer.app.comments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tealer.app.R;
import com.tealer.app.activity.BaseActivity;
import com.tealer.app.utils.BitmapDisplayConfigHelper;
import com.tealer.app.utils.ImageLoaderHelper;
import com.tealer.app.widget.ImageCycleView;

/**
 * Author：pengbo on 2016/3/27 17:30
 * Email：1162947801@qq.com
 */
public class BigImageActivity extends BaseActivity {
    //  private JSONArray json = null;

    private ImageCycleView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_activity_bigimage);
        //  String jsonStr = getIntent().getStringExtra("jsonStr");

        String[] images=getIntent().getStringArrayExtra("images");
        //   json = JSONArray.parseArray(jsonStr);
//        if (json == null) {
//            finish();
//            return;
//        }

        int page = getIntent().getIntExtra("page", 0);

        mAdView = (ImageCycleView) this.findViewById(R.id.ad_view);
        mAdView.setImageResources(images, page, mAdCycleViewListener);

    }





    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {

            // finish();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoaderHelper.GetInstance().display(imageView,imageURL,BitmapDisplayConfigHelper.GetInstance().getRectIconBitmapUtilsConfig());
        }
    };

}

