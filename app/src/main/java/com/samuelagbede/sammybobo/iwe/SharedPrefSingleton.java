package com.samuelagbede.sammybobo.iwe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Agbede on 28/09/2016.
 */

public class SharedPrefSingleton extends Application {
    Context mContext;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static SharedPrefSingleton mInstance;

    private SharedPrefSingleton(Context context){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences("user_prefs", 0);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static SharedPrefSingleton getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefSingleton(context);
        }
        return mInstance;
    }

    public void preferencePutInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int preferenceGetInteger(String key) {
        if (key == "font_size"){
            return sharedPreferences.getInt(key, 18);
        }
        return sharedPreferences.getInt(key, 18);
    }

    public void preferencePutBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean preferenceGetBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void preferencePutString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String preferenceGetFontString(String key) {
        return sharedPreferences.getString(key, "default");
    }
    public void preferencePutStringFontName(String key) {
        editor.putString(key, "default");
        editor.commit();
    }

    public String preferenceGetStringName(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }


    public void preferencePutLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long preferenceGetLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void preferenceRemoveKey(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clearPreference() {
        editor.clear();
        editor.commit();
    }

}
