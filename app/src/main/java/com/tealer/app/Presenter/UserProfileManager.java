package com.tealer.app.Presenter;

import android.content.Context;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.tealer.app.HXSDKHelper;
import com.tealer.app.HXSDKHelper.DataSyncListener;
import com.tealer.app.domain.User;
import com.tealer.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：pengbo on 2016/3/12 22:19
 * Email：1162947801@qq.com
 */
public class UserProfileManager {
    /**
     * application context
     */
    protected Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init
     * again
     */
    private boolean sdkInited = false;

    /**
     * HuanXin sync contact nick and avatar listener
     */
    private List<DataSyncListener> syncContactInfosListeners;

    private boolean isSyncingContactInfosWithServer = false;

    private User currentUser;

    public UserProfileManager() {
    }

    public synchronized boolean init(Context context) {
        if (sdkInited) {
            return true;
        }
        syncContactInfosListeners = new ArrayList<DataSyncListener>();
        sdkInited = true;
        return true;
    }

    public void addSyncContactInfoListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.add(listener);
        }
    }

    public void removeSyncContactInfoListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.remove(listener);
        }
    }

    public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<User>> callback) {
        if (isSyncingContactInfosWithServer) {
            return;
        }
        isSyncingContactInfosWithServer = true;
//        ParseManager.getInstance().getContactInfos(usernames, new EMValueCallBack<List<EaseUser>>() {
//
//            @Override
//            public void onSuccess(List<EaseUser> value) {
//                isSyncingContactInfosWithServer = false;
//                // in case that logout already before server returns,we should
//                // return immediately
//                if (!DemoHelper.getInstance().isLoggedIn()) {
//                    return;
//                }
//                if (callback != null) {
//                    callback.onSuccess(value);
//                }
//            }
//
//            @Override
//            public void onError(int error, String errorMsg) {
//                isSyncingContactInfosWithServer = false;
//                if (callback != null) {
//                    callback.onError(error, errorMsg);
//                }
//            }
//
//        });

    }

    public void notifyContactInfosSyncListener(boolean success) {
        for (DataSyncListener listener : syncContactInfosListeners) {
            listener.onSyncComplete(success);
        }
    }

    public boolean isSyncingContactInfoWithServer() {
        return isSyncingContactInfosWithServer;
    }

    public synchronized void reset() {
        isSyncingContactInfosWithServer = false;
        currentUser = null;
        PreferenceManager.getInstance().removeCurrentUserInfo();
    }

    public synchronized User getCurrentUserInfo() {
        if (currentUser == null) {
            String username = EMClient.getInstance().getCurrentUser();
            currentUser = new User(username);
            String nick = getCurrentUserNick();
            currentUser.setNick((nick != null) ? nick : username);
            currentUser.setAvatar(getCurrentUserAvatar());
        }
        return currentUser;
    }

    public boolean updateCurrentUserNickName(final String nickname) {
//        boolean isSuccess = ParseManager.getInstance().updateParseNickName(nickname);
//        if (isSuccess) {
//            setCurrentUserNick(nickname);
//        }
        return false;
    }

    public String uploadUserAvatar(byte[] data) {
//        String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
//        if (avatarUrl != null) {
//            setCurrentUserAvatar(avatarUrl);
//        }
        return "";
    }

    public void asyncGetCurrentUserInfo() {
//        ParseManager.getInstance().asyncGetCurrentUserInfo(new EMValueCallBack<EaseUser>() {
//
//            @Override
//            public void onSuccess(EaseUser value) {
//                if(value != null){
//                    setCurrentUserNick(value.getNick());
//                    setCurrentUserAvatar(value.getAvatar());
//                }
//            }
//
//            @Override
//            public void onError(int error, String errorMsg) {
//
//            }
//        });

    }
    public void asyncGetUserInfo(final String username,final EMValueCallBack<User> callback){

    }
    private void setCurrentUserNick(String nickname) {
        getCurrentUserInfo().setNick(nickname);
        PreferenceManager.getInstance().setCurrentUserNick(nickname);
    }

    private void setCurrentUserAvatar(String avatar) {
        getCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }
}
