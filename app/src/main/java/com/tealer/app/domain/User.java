package com.tealer.app.domain;

import android.annotation.SuppressLint;

import com.hyphenate.chat.EMContact;

/**
 * Author：pengbo on 2016/3/12 16:53
 * Email：1162947801@qq.com
 */
@SuppressLint("ParcelCreator")
public class User extends EMContact {
    private int unreadMsgCount;
    private String header;
    private String usernick;
    private String sex;
    private String tel;
    private String fxid;
    private String region;
    private String avatar;
    private String sign;
    private String beizhu;

    public User(String username){
        super(username);
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public void setNick(String usernick){
        this.usernick=usernick;
    }
    public String getNick(){
        return usernick;
    }
    public void setAvatar(String avatar){
        this.avatar=avatar;
    }
    public String getAvatar(){
        return avatar;
    }
    public void setTel(String tel){
        this.tel=tel;
    }
    public String getTel(){
        return tel;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
    public void setFxid(String fxid) {
        this.fxid = fxid;
    }

    public String getFxid() {
        return fxid;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getBeizhu() {
        return beizhu;
    }
    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        return getUsername().equals(((User) o).getUsername());
    }

    @Override
    public String toString() {
        return username;
    }

}