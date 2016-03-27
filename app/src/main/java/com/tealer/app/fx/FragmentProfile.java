package com.tealer.app.fx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tealer.app.Constant;
import com.tealer.app.HXSDKHelper;
import com.tealer.app.R;
import com.tealer.app.comments.SocialFriendActivity;
import com.tealer.app.utils.BitmapDisplayConfigHelper;
import com.tealer.app.utils.ImageLoaderHelper;

/**
 * Author：pengbo on 2016/3/27 22:21
 * Email：1162947801@qq.com
 */
public class FragmentProfile extends Fragment {

    private String avatar = "";
    private ImageView iv_avatar;
    private TextView tv_name;
    TextView tv_fxid;
    String fxid;
    String nick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RelativeLayout re_myinfo = (RelativeLayout) getView().findViewById(
                R.id.re_myinfo);
        re_myinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),
                        MyUserInfoActivity.class));
            }

        });
        RelativeLayout re_setting = (RelativeLayout) getView().findViewById(
                R.id.re_setting);
        re_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }

        });

        RelativeLayout re_money_bag = (RelativeLayout) getView().findViewById(
                R.id.re_money_bag);
        re_money_bag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }

        });
        nick = HXSDKHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getNick();
        fxid = HXSDKHelper.getInstance().getCurrentUsernName();

        avatar =HXSDKHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getAvatar();
        iv_avatar = (ImageView) re_myinfo.findViewById(R.id.iv_avatar);
        tv_name = (TextView) re_myinfo.findViewById(R.id.tv_name);
        tv_fxid = (TextView) re_myinfo.findViewById(R.id.tv_fxid);
        tv_name.setText(nick);
        if (fxid.equals("0")) {
            tv_fxid.setText("微信号：未设置");
        } else {
            tv_fxid.setText("微信号:" + fxid);
        }
        showUserAvatar(iv_avatar, avatar);


        getView().findViewById(R.id.re_xiangce).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userID=HXSDKHelper.getInstance().getCurrentUsernName();
                if(!TextUtils.isEmpty(userID)){

                    startActivity(new Intent(getActivity(),SocialFriendActivity.class).putExtra("friendID", userID));

                }
            }


        });
        getView().findViewById(R.id.re_rewards).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),AlipayMeActivity.class));


            }


        });

    }

    private void showUserAvatar(ImageView iamgeView, String avatar) {
        final String url_avatar = Constant.URL_Avatar + avatar;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
            ImageLoaderHelper.GetInstance().display(iamgeView, url_avatar, BitmapDisplayConfigHelper.GetInstance().getIconBitmapUtilsConfig());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String vatar_temp =HXSDKHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getAvatar();
        if (!vatar_temp.equals(avatar)) {
            showUserAvatar(iv_avatar, avatar);
        }

        String nick_temp = HXSDKHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getNick();
        String fxid_temp =HXSDKHelper.getInstance().getCurrentUsernName();
        if (!nick_temp.equals(nick)) {
            tv_name.setText(nick_temp);
        }
        if (!fxid_temp.equals(fxid)) {
            if (fxid_temp.equals("0")) {
                tv_fxid.setText("微信号：未设置");
            } else {
                tv_fxid.setText("微信号:" + fxid_temp);
            }
        }
    }

}

