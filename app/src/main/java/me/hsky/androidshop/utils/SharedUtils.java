package me.hsky.androidshop.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by Administrator on 2016/4/20.
 * 实现标记的写入和读取
 */
public class SharedUtils{
    private static final String FILE_NAME = "shop";
    private static final String MODE_NAME = "welcome";

    /*获取boolean类型的值*/
    public static boolean getWelcomeBoolean(Context context){
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, false);
    }

    /*写入boolean类型的值*/
    public static void setWelcomeBoolean(Context context, boolean isFirst){
        Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(MODE_NAME, isFirst);
        editor.commit();
    }
}
