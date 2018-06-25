package com.anonimeact.donativemask;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Didi Yulianto (anonimeact) on 27/01/2017.
 * author email didiyuliantos@gmail.com
 */

public class Database {

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor editor;

    public static void saveByGroup(Context context, String dataString, String key, String group) {
        sharedPrefs = context.getSharedPreferences(group, MODE_PRIVATE);
        editor = sharedPrefs.edit();
        editor.putString(key, Blind.eKey(dataString)).apply();
//        if (isUnix) editor.putString(key, Blind.eUnix(dataString)).apply();
//        else editor.putString(key, Blind.e(dataString)).apply();
    }

    public static String getByGroup(Context context, String key, String group) {
        sharedPrefs = context.getSharedPreferences(group, MODE_PRIVATE);
        if (sharedPrefs.getString(key, "").length() < 1) {
            return "";
        } else {
            return Blind.dKey(sharedPrefs.getString(key, ""));
//            if (isUnix) return Blind.dUnix(sharedPrefs.getString(key, ""));
//            else return Blind.d(sharedPrefs.getString(key, ""));
        }
    }

    public static void save(Context context, String dataString, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.putString(key, Blind.eKey(dataString)).apply();
        //        if (isUnix) editor.putString(key, Blind.eUnix(dataString)).apply();
//        else editor.putString(key, Blind.e(dataString)).apply();
    }

    public static String get(Context context, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPrefs.getString(key, "").length() < 1) {
            return "";
        } else {
            return Blind.dKey(sharedPrefs.getString(key, ""));
//            if (isUnix) return Blind.dUnix(sharedPrefs.getString(key, ""));
//            else return Blind.d(sharedPrefs.getString(key, ""));
        }
    }

    public static void setCache(Context context, String content, String key) {
        try {
            String data = Blind.eKey(content);
            String keyUnix = Blind.eKey(key);
            File file;
            FileOutputStream outputStream;
            try {
                file = new File(context.getCacheDir(), keyUnix);
                outputStream = new FileOutputStream(file);
                outputStream.write(data.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCache(Context context, String key) {
        BufferedReader input = null;
        File file = null;
        try {
            String keyUnix = Blind.eKey(key);
            file = new File(context.getCacheDir(), keyUnix);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            return Blind.dKey(buffer.toString());
        } catch (IOException e) {
            return "";
        }
    }

    public static void removeDataCustom(Context context, String group) {
        sharedPrefs = context.getSharedPreferences(group, MODE_PRIVATE);
        editor = sharedPrefs.edit();
        editor.clear().apply();
    }

    public static void removeDataDefault(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPrefs.edit();
        editor.clear().apply();
    }

    public static void removeCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        assert dir != null;
        return dir.delete();
    }
}
