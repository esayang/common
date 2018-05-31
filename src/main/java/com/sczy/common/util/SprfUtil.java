package com.sczy.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * {@link SharedPreferences}工具类
 * <p/>
 * Created by shengl on 2015/4/21.
 */
public class SprfUtil {

    private final static String FILE_NAME = "appShareprefrence";

    private static Context context;

    private static SharedPreferences preferences() {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context applicationContext) {
        context = applicationContext;
    }


    private static SharedPreferences.Editor editor() {
        return preferences().edit();
    }

    public static void setInt(String key, int value) {
        editor().putInt(key, value).commit();
    }

    public static int getInt(String key, int defVale) {
        return preferences().getInt(key, defVale);
    }

    public static void setString(String key, String value) {
        editor().putString(key, value).commit();
    }

    public static String getString(String key, String defVale) {
        return preferences().getString(key, defVale);
    }

    public static void setBoolean(String key, boolean value) {
        editor().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defVale) {
        return preferences().getBoolean(key, defVale);
    }

    public static void setLong(String key, long value) {
        editor().putLong(key, value).commit();
    }

    public static long getLong(String key, long value) {
        return preferences().getLong(key, value);
    }

    public static void setFloat(String key, float value) {
        editor().putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defVale) {
        return preferences().getFloat(key, defVale);
    }
}
