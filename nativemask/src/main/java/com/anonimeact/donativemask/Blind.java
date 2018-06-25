package com.anonimeact.donativemask;

import android.os.Build;
import android.support.annotation.Size;

/**
 * Created by Didi Yulianto (anonimeact) on 17/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class Blind {
    private static String unixs() {
        /**
         * This message used to get unix
         * thing from user for temporary saved things
         */

        return Build.ID + Build.HOST + Build.BOARD + "";
    }

    public static String e(@Size(max=115) String x) {
        if (x.equals(""))
            return "";
        else
            return e(x, unixs(), false);
    }

    private static String eUnix(String x) {
        if (x.equals(""))
            return "";
        else
            return e(x, unixs(), true);
    }

    public static String d(String x) {
        if (x.equals(""))
            return "";
        else
            return d(x, unixs(), false);
    }

    private static String dUnix(String x) {
        if (x.equals(""))
            return "";
        else
            return d(x, unixs(), true);
    }

    private static native String e(String x, String y, boolean z);

    private static native String d(String x, String y, boolean z);


    public static String reference(String[] x) {
        return z(x, unixs());
    }

    private static native String z(String[] x, String y);

    public static native String eKey(@Size(min=3, max=240) String x);

    public static native String dKey(String x);

    static {
        System.loadLibrary("base-lib");
    }
}