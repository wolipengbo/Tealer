package com.tealer.app.fx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tealer.app.HXSDKHelper;
import com.tealer.app.R;
import com.tealer.app.TealerApplication;

/**
 * Author：pengbo on 2016/3/26 11:35
 * Email：1162947801@qq.com
 */
public class FragmentFind extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.re_friends).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userID= HXSDKHelper.getInstance().getCurrentUsernName();
                if(!TextUtils.isEmpty(userID)){

                    startActivity(new Intent(getActivity(),SocialMainActivity.class).putExtra("userID", userID));

                }
            }


        });

    }



}
