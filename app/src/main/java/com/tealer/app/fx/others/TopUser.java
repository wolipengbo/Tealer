package com.tealer.app.fx.others;

/**
 * Author：pengbo on 2016/3/12 22:33
 * Email：1162947801@qq.com
 */
public class TopUser {
    private long  time;
    private String userName;
    private int is_group;

    public void setTime(long  time){
        this.time=time;
    }
    public long getTime(){
        return time;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setType(int  is_group){
        this.is_group=is_group;
    }
    public int getType(){
        return is_group;
    }
}
