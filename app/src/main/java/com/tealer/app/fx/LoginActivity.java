package com.tealer.app.fx;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.HanziToPinyin;
import com.tealer.app.Constant;
import com.tealer.app.HXSDKHelper;
import com.tealer.app.R;
import com.tealer.app.TealerApplication;
import com.tealer.app.activity.BaseActivity;
import com.tealer.app.db.UserDao;
import com.tealer.app.domain.User;
import com.tealer.app.engine.GetFriendsListEngine;
import com.tealer.app.engine.LoginEngine;
import com.tealer.app.http.HttpRequestCallBack;
import com.tealer.app.utils.EncodeUtils;
import com.tealer.app.utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author：pengbo on 2016/3/13 15:43
 * Email：1162947801@qq.com
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.et_usertel)
    private EditText et_usertel;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.btn_qtlogin)
    private Button btn_qtlogin;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(LoginActivity.this);
        // 监听多个输入框
        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.setMessage("正在登录...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                final String password = et_password.getText().toString().trim();
                String usertel = et_usertel.getText().toString().trim();
                Map<String, Object> map = new HashMap<String, Object>();

                map.put("usertel", usertel);
                map.put("password", password);
                LoginEngine.getResult(new HttpRequestCallBack(){
                    @Override
                    public void onStarted() {
                        super.onStarted();
                    }

                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        String strJson=EncodeUtils.removeBOM(result);
                        JSONObject data=LoginEngine.parseResult(strJson,LoginActivity.this);
                        if (data == null) {
                            Toast.makeText(LoginActivity.this,
                                    "返回数据错误../", Toast.LENGTH_SHORT)
                                    .show();
                            return ;
                        }
                        try {
                            int code = data.getInt("code");
                            if (code == 1) {

                                JSONObject json = data.getJSONObject("user");
                                login(json);
                            } else if (code == 2) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "账号或密码错误...", Toast.LENGTH_SHORT)
                                        .show();
                            } else if (code == 3) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "服务器端注册失败...", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "服务器繁忙请重试...", Toast.LENGTH_SHORT)
                                        .show();
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "数据解析错误...",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        super.onError(ex, isOnCallback);
                    }
                },map,LoginActivity.this);
            }

        });
        btn_qtlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }

        });
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {

            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;

            if (Sign2 & Sign3) {
                btn_login.setTextColor(0xFFFFFFFF);
                btn_login.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_login.setTextColor(0xFFD0EFC6);
                btn_login.setEnabled(false);
            }
        }

    }

    private void login(final JSONObject json) {

        try {
            final String nick = json.getString("nick");
            final String hxid = json.getString("hxid");
            final String password = json.getString("password");
             String fxid = json.getString("fxid");
             String tel = json.getString("tel");
             String sex = json.getString("sex");
             String sign = json.getString("sign");
             String avatar = json.getString("avatar");
             String region = json.getString("region");
            // 调用sdk登陆方法登陆聊天服务器
            EMClient.getInstance().login(hxid, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    // 登陆成功，保存用户名密码
                    HXSDKHelper.getInstance().setCurrentUserName(hxid);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.setMessage(getString(R.string.list_is_for));
                        }
                    });
                    try {
                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                        // ** manually load all local groups and
                        // conversations in case we are auto login
                        // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                        boolean updatenick =EMClient.getInstance().updateCurrentUserNick(nick);
                        if (!updatenick) {
                            Log.e("LoginActivity",
                                    "update current user nick fail");
                        }
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        // 处理好友和群组
                        runOnUiThread(new Runnable() {
                            public void run() {
                                processContactsAndGroups(json);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        // 取好友或者群聊失败，不让进入主页面
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                HXSDKHelper.getInstance().logout(false,null);
                                Toast.makeText(getApplicationContext(),
                                        R.string.login_failure_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }

                @Override
                public void onError(int i,final String message) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.Login_failed) + message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        } catch (JSONException e1) {

            e1.printStackTrace();
        }

    }

    private void processContactsAndGroups(final JSONObject json) {
        // demo中简单的处理成每次登陆都去获取好友username，开发者自己根据情况而定

        // try {
        // List<String> usernames = EMContactManager.getInstance()
        // .getContactUserNames();
        // if (usernames != null && usernames.size() > 0) {
        // String totaluser = usernames.get(0);
        // for (int i = 1; i < usernames.size(); i++) {
        // final String split = "66split88";
        // totaluser += split + usernames.get(i);
        // }
        // totaluser = totaluser
        // .replace(Constant.NEW_FRIENDS_USERNAME, "");
        // totaluser = totaluser.replace(Constant.GROUP_USERNAME, "");
        // Log.e("totaluser---->>>>>",totaluser);
        Map<String, Object> map = new HashMap<String, Object>();

        // map.put("uids", totaluser);
        map.put("hxid", HXSDKHelper.getInstance().getCurrentUsernName());
        GetFriendsListEngine.getResult(new HttpRequestCallBack(){
            @Override
            public void onStarted() {
                super.onStarted();
            }

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                String strJson=EncodeUtils.removeBOM(result);
                JSONObject data=LoginEngine.parseResult(strJson,LoginActivity.this);

                try {
                    int code = data.getInt("code");
                    if (code == 1000) {
                        JSONArray josnArray = data.getJSONArray("friends");
                        // 己的信息
                        saveMyInfo(json);

                        saveFriends(josnArray);

                    }
                    // else if (code == 2) {
                    // dialog.dismiss();
                    // Toast.makeText(LoginActivity.this,
                    // "获取好友列表失败,请重试...", Toast.LENGTH_SHORT)
                    // .show();
                    // }
                    else {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "服务器繁忙请重试...",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "数据解析错误...",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }




            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        },map,LoginActivity.this);
        // } else {
        // // 己的信息
        // saveMyInfo(json);
        //
        // saveFriends(null);
        // }
        // } catch (EaseMobException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }

    }

    /**
     * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username
     * @param user
     */
    @SuppressLint("DefaultLocale")
    protected void setUserHearder(String username, User user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        headerName = headerName.trim();
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance()
                    .get(headerName.substring(0, 1)).get(0).target.substring(0,
                            1).toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }

    private void saveMyInfo(JSONObject json) {

        try {
            String hxid = json.getString("hxid");
            String fxid = json.getString("fxid");
            String nick = json.getString("nick");
            String avatar = json.getString("avatar");
            String password = json.getString("password");
            String sex = json.getString("sex");
            String region = json.getString("region");
            String sign = json.getString("sign");
            String tel = json.getString("tel");
            String money  = json.getString("money");
            PreferenceManager.getInstance().setCurrentUserName(hxid);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_FXID, fxid);
            PreferenceManager.getInstance().setCurrentUserNick(nick);
            PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_PASSWORD, password);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_SEX,sex);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_REGION,region);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_SIGN,sign);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_TEL,tel);
            PreferenceManager.getInstance().setString(PreferenceManager.SHARED_KEY_CURRENTUSER_MONEY,money);
        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
            return;
        }

    }

    private void saveFriends(JSONArray josnArray) {

        Map<String, User> map = new HashMap<String, User>();

        if (josnArray != null) {
            for (int i = 0; i < josnArray.length(); i++) {
                try {
                    JSONObject json = josnArray.getJSONObject(i);
                    String hxid = json.getString("hxid");
                    String fxid = json.getString("fxid");
                    String nick = json.getString("nick");
                    String avatar = json.getString("avatar");
                    String sex = json.getString("sex");
                    String region = json.getString("region");
                    String sign = json.getString("sign");
                    String tel = json.getString("tel");

                    User user = new User(hxid);
                    user.setFxid(fxid);
                    user.setBeizhu("");
                    user.setNick(nick);
                    user.setRegion(region);
                    user.setSex(sex);
                    user.setTel(tel);
                    user.setSign(sign);
                    user.setAvatar(avatar);
                    setUserHearder(hxid, user);
                    map.put(hxid, user);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        // 添加user"申请与通知"
        User newFriends = new User(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(
                R.string.Application_and_notify);
        newFriends.setNick(strChat);
        newFriends.setBeizhu("");
        newFriends.setFxid("");
        newFriends.setHeader("");
        newFriends.setRegion("");
        newFriends.setSex("");
        newFriends.setTel("");
        newFriends.setSign("");
        newFriends.setAvatar("");
        map.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User(Constant.GROUP_USERNAME);
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        groupUser.setNick(strChat);
        groupUser.setBeizhu("");
        groupUser.setFxid("");
        groupUser.setHeader("");
        groupUser.setRegion("");
        groupUser.setSex("");
        groupUser.setTel("");
        groupUser.setSign("");
        groupUser.setAvatar("");
        map.put(Constant.GROUP_USERNAME, groupUser);

        // 存入内存
        HXSDKHelper.getInstance().setContactList(map);
        // 存入db
        UserDao dao = new UserDao(LoginActivity.this);
        List<User> users = new ArrayList<User>(map.values());
        dao.saveContactList(users);

        // 获取黑名单列表

//        try {
//            List<String> blackList = EMContactManager.getInstance()
//                    .getBlackListUsernamesFromServer();
//            EMContactManager.getInstance().saveBlackList(blackList);

        // 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
        getGroups() ;
        //       addContact("11223354");
        if (!LoginActivity.this.isFinishing())
            dialog.dismiss();
        // 进入主页面
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();


    }
    private void getGroups() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
    /**
     * 添加contact
     *
     * @param view
     */
//    @SuppressLint("ShowToast")
//    public void addContact(final String glufine_id) {
//        // 11223354
//        if (glufine_id == null || glufine_id.equals("")) {
//            return;
//        }
//
//        if (MYApplication.getInstance().getUserName().equals(glufine_id)) {
//
//            return;
//        }
//
//        if (MYApplication.getInstance().getContactList()
//                .containsKey(glufine_id)) {
//
//            return;
//        }
//
//        new Thread(new Runnable() {
//            public void run() {
//
//                try {
//                    // 在reason封装请求者的昵称/头像/时间等信息，在通知中显示
//
//                    String name = LocalUserInfo.getInstance(LoginActivity.this)
//                            .getUserInfo("nick");
//                    String avatar = LocalUserInfo.getInstance(
//                            LoginActivity.this).getUserInfo("avatar");
//                    long time = System.currentTimeMillis();
//
//                    String reason = name + "66split88" + avatar + "66split88"
//                            + String.valueOf(time) + "66split88" + "加你好友";
//                    EMContactManager.getInstance().addContact(glufine_id,
//                            reason);
//
//                } catch (final Exception e) {
//
//                }
//            }
//        }).start();
//    }
}
