package me.hsky.androidshop.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by Administrator on 2016/4/20.
 * 实现标记的写入和读取
 */
public class SharedUtils {
    private static final String FILE_NAME = "shop";

    /*获取boolean类型的值*/
    public static boolean getWelcomeBoolean(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean("welcome", false);
    }

    /*写入boolean类型的值*/
    public static void setWelcomeBoolean(Context context, boolean isFirst) {
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean("welcome", isFirst);
        editor.commit();
    }

    /*获取boolean类型的值*/
    public static String getCityName(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("cityName", "选择城市");
    }

    /*写入boolean类型的值*/
    public static void setCityName(Context context, String cityName) {
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("cityName", cityName);
        editor.commit();
    }

    /*记录登录状态*/
    public static Boolean getUserLoginStatus(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean("userLoginStatus", false);
    }
    /*设置登录状态*/
    public static void setUserLoginStatus(Context context, Boolean loginStatus){
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean("userLoginStatus", loginStatus);
        editor.commit();
    }

    /*记录登录账号token*/
    public static String getUserLoginToken(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("userLoginToken", "");
    }
    /*设置登录状态*/
    public static void setUserLoginToken(Context context, String userLoginToken){
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("userLoginToken", userLoginToken);
        editor.commit();
    }

    /*记录登录账号token*/
    public static String getUserId(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("userId", "");
    }
    /*设置登录状态*/
    public static void setUseId(Context context, String userId){
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("userId", userId);
        editor.commit();
    }
}
