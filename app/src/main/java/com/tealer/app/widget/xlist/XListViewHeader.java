package com.tealer.app.widget.xlist;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tealer.app.R;
import com.tealer.app.widget.wave.WaveView;

/**
 * Created by lipengbo on 2015/7/13.
 */
public class XListViewHeader extends LinearLayout {
    private FrameLayout mContainer;

    private WaveView headerLoadingProgressBar;
    private ImageView headerLoadingLogo;

    private int mState = STATE_NORMAL;


    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_SLEEP = 3;

    public boolean isSleep = false;
    private int startProgress = 0;

    public XListViewHeader(Context context)
    {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context)
    {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, 0);
        mContainer = (FrameLayout) LayoutInflater.from(context).inflate( R.layout.refresh_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        headerLoadingProgressBar = (WaveView) mContainer.findViewById(R.id.refresh_header_loading_progressbar);
        headerLoadingLogo = (ImageView) mContainer.findViewById(R.id.refresh_header_loading_logo);
        startProgress = (int)getResources().getDimension(R.dimen.refresh_header_progress_padding);
//        int maxProgress = (int) getResources().getDimension(R.dimen.actionbar_height) - startProgress;
//        headerRoundProgressBar.setMax(maxProgress);
//        headerRoundProgressBar.setCricleColor(0xf1f1f1);
    }


    public void setState(int state)
    {
        if (state == mState)
            return;

//        if (state == STATE_REFRESHING)
//        {
//            headerRoundProgressBar.setVisibility(View.GONE);
//            headerLoadingProgressBar.setVisibility(View.VISIBLE);
//            headerLoadingText.setVisibility(View.GONE);
//        }
//        else if(state == STATE_SLEEP)
//        {
//            headerRoundProgressBar.setVisibility(View.GONE);
//            headerLoadingProgressBar.setVisibility(View.GONE);
//            headerLoadingText.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            headerRoundProgressBar.setVisibility(View.VISIBLE);
//            headerLoadingProgressBar.setVisibility(View.GONE);
//            headerLoadingText.setVisibility(View.GONE);
//        }
        headerLoadingProgressBar.setVisibility(View.VISIBLE);
        headerLoadingLogo.setVisibility(View.VISIBLE);

        mState = state;
    }

    public void setVisiableHeight(int height)
    {
        if (height < 0)
            height = 0;

        if(startProgress < height)
        {
            int progress = height - startProgress;
            headerLoadingProgressBar.setProgress( progress >= 80 ? 80 : progress);
        }
        else
            headerLoadingProgressBar.setProgress(0);

        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight()
    {
        return mContainer.getHeight();
    }
}
