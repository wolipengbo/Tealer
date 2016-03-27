package com.tealer.app.comments;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tealer.app.HXSDKHelper;
import com.tealer.app.R;
import com.tealer.app.activity.BaseActivity;
import com.tealer.app.domain.User;
import com.tealer.app.engine.SocialApiTaskURL_SOCIAL_FRIENDEngine;
import com.tealer.app.http.HttpRequestCallBack;
import com.tealer.app.utils.EncodeUtils;
import com.tealer.app.widget.xlist.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：pengbo on 2016/3/27 17:07
 * Email：1162947801@qq.com
 */
public class SocialFriendActivity extends BaseActivity implements XListView.IXListViewListener {

    private XListView pull_refresh_list;
    private List<JSONObject> articles = new ArrayList<JSONObject>();

    // private JSONArray datas = new JSONArray();
    private SocialFriendAdapter adapter;
    private ListView actualListView;
    private int page = 0;

    String userID;
    List<String> sIDs = new ArrayList<String>();
    String friendID;

    @Override
    public void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        setContentView(R.layout.activity_social_friend);
        userID = HXSDKHelper.getInstance().getCurrentUsernName();

        System.out.println("上传数据------->>>>>>>>" + "userID" + ":" + userID);

        friendID = this.getIntent().getStringExtra("friendID");
        // if(friendID==null){
        // finish();
        // return;
        // }
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        // 此处应该换成昵称

        String nick_temp = friendID;
        if (friendID.equals(userID)) {
            nick_temp =HXSDKHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getNick();

        } else {
            User user = HXSDKHelper.getInstance().getContactList().get(friendID);
            if (user != null) {
                nick_temp = user.getNick();
            }
        }

        tv_title.setText(nick_temp);
        initView();
    }

    private void initView() {
        pull_refresh_list = (XListView) findViewById(R.id.pull_refresh_list);
        adapter = new SocialFriendAdapter(SocialFriendActivity.this, articles);
        actualListView.setAdapter(adapter);
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position != 1) {
                    Log.e("position----->>", String.valueOf(position));
                    JSONObject json = adapter.getJSONs().get(position - 2);
//                    startActivity(new Intent(SocialFriendActivity.this,
//                            SocialDetailActivity.class).putExtra("json",
//                            json.toJSONString()));
                }
            }

        });
        getData(0);

    }

    private void getData(final int page_num) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userID", userID);
        map.put("friendID", friendID);
        map.put("num", String.valueOf(page_num));
        SocialApiTaskURL_SOCIAL_FRIENDEngine.getResult(new HttpRequestCallBack(){


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                String str= EncodeUtils.removeBOM(result);
                JSONObject  data=SocialApiTaskURL_SOCIAL_FRIENDEngine.parseResult(str, SocialFriendActivity.this);
                pull_refresh_list.stopRefresh();
                if (data == null) {

                    return;

                }
                int code = data.getInteger("code");
                if (code == 1000) {
                    JSONArray users_temp = data.getJSONArray("data");
                    String time = data.getString("time");
                    HXSDKHelper.getInstance().setTime(time);
                    if (page_num == 0) {

                        // datas = users_temp;
                        articles.clear();
                        sIDs.clear();
                        for (int i = 0; i < users_temp.size(); i++) {
                            JSONObject json = users_temp.getJSONObject(i);
                            String sID = json.getString("sID");
                            sIDs.add(sID);
                            articles.add(json);
                        }

                    } else {

                        Map<String, JSONObject> map = new HashMap<String, JSONObject>();

                        for (int i = 0; i < users_temp.size(); i++) {
                            JSONObject json = users_temp.getJSONObject(i);
                            String sID = json.getString("sID");
                            if (!sIDs.contains(sID)) {
                                sIDs.add(sID);
                                articles.add(json);
                            }
                        }

                    }
                    // adapter = new
                    // SocialFriendAdapter(SocialMainActivity.this,
                    // datas, time);
                    // actualListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    // ACache.get(getActivity()).put("last_login", users);

                } else {
                    // ToastUtil.showMessage("服务器出错...");
                }

            }
        },map,SocialFriendActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData(0);
    }

    @Override
    public void onRefresh() {
        page = 0;
        getData(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData(page);

    }
}
