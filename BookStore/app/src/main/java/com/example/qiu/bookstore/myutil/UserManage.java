package com.example.qiu.bookstore.myutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.qiu.bookstore.models.User;
import com.example.qiu.bookstore.models.UserAndResponse;

/**
 * Created by qiu on 2018/3/25.
 */

public class UserManage {
    private  static  UserManage instance;
    private UserManage(){

    }

    public static UserManage getInstance() {
        if(instance==null)
            instance=new UserManage();
        return instance;
    }
    public void saveUserInfo(Context context,String username,String passwd,int userId){
        SharedPreferences sp=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("USER_NAME",username);
        editor.putString("PASSWORD",passwd);
        editor.putInt("USERID",userId);
        editor.commit();
    }
    public UserAndResponse getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        UserAndResponse userAndResponse=new UserAndResponse();
        userAndResponse.setName(sp.getString("USER_NAME"," "));
        userAndResponse.setPassword(sp.getString("PASSWORD"," "));
        userAndResponse.setId(sp.getInt("USERID",0));
        return userAndResponse;
    }
    public boolean hasUserInfo(Context context){
        UserAndResponse userAndResponse=getUserInfo(context);
        if (userAndResponse != null) {
            if ((!TextUtils.isEmpty(userAndResponse.getName())) && (!TextUtils.isEmpty(userAndResponse.getPassword()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
