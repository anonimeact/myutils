package com.anonimeact.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.compat.BuildConfig;

/**
 * Created by Didi Yulianto (anonimeact) on 04/10/2017.
 * author email didiyuliantos@gmail.com
 */

public class SPHelper {
    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor editor;

    public static void saveUriCapture(Context context, String uri) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putString(BuildConfig.APPLICATION_ID + "0", uri).apply();
    }

    public static String getUriCapture(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(BuildConfig.APPLICATION_ID + "0", "");
    }

    public static void saveBoolean(Context context, boolean is, String key){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putBoolean(key, is).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean(key, false);
    }

    public static void saveString(Context context, String is, String key){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putString(key, is).apply();
    }

    public static String getString(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "");
    }

    public static void saveInt(Context context, int dat, String key){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putInt(key, dat).apply();
    }

    public static int getInt(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getInt(key, 0);
    }

    public static void saveLong(Context context, long dat, String key){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putLong(key, dat).apply();
    }

    public static long getLong(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getLong(key, 0);
    }

    public static void saveFloat(Context context, float dat, String key){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putFloat(key, dat).apply();
    }

    public static float getFloat(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getFloat(key, 0.0f);
    }
}
