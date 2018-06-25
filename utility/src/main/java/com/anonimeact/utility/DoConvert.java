package com.anonimeact.utility;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Didi Yulianto (anonimeact) on 27/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoConvert {
    public static String fromTimeStamp(long unix, String format) {
        Date date = new Date(unix * 1000L); // *1000 is to convert seconds to milliseconds
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH); // the format of your date
        String fin = sdf.format(date);

        if (fin.contains("Jan")) {
            fin = fin.replace("Jan", "Januari");
        } else if (fin.contains("Feb")) {
            fin = fin.replace("Feb", "Februari");
        } else if (fin.contains("Mar")) {
            fin = fin.replace("Mar", "Maret");
        } else if (fin.contains("Apr")) {
            fin = fin.replace("Apr", "April");
        } else if (fin.contains("May")) {
            fin = fin.replace("May", "Mei");
        } else if (fin.contains("Jun")) {
            fin = fin.replace("Jun", "Juni");
        } else if (fin.contains("Jul")) {
            fin = fin.replace("Jul", "Juli");
        } else if (fin.contains("Aug")) {
            fin = fin.replace("Aug", "Agustus");
        } else if (fin.contains("Sep")) {
            fin = fin.replace("Sep", "September");
        } else if (fin.contains("Oct")) {
            fin = fin.replace("Oct", "Oktober");
        } else if (fin.contains("Nov")) {
            fin = fin.replace("Nov", "November");
        } else if (fin.contains("Dec")) {
            fin = fin.replace("Dec", "Desember");
        }

        if (fin.toLowerCase().contains("sunday")) {
            return fin.replace("Sunday", "Minggu");
        } else if (fin.toLowerCase().contains("monday")) {
            return fin.replace("Monday", "Senin");
        } else if (fin.toLowerCase().contains("tuesday")) {
            return fin.replace("Tuesday", "Selasa");
        } else if (fin.toLowerCase().contains("wednesday")) {
            return fin.replace("Wednesday", "Rabu");
        } else if (fin.toLowerCase().contains("thursday")) {
            return fin.replace("Thursday", "Kamis");
        } else if (fin.toLowerCase().contains("friday")) {
            return fin.replace("Friday", "Jumat");
        } else if (fin.toLowerCase().contains("saturday")) {
            return fin.replace("Saturday", "Sabtu");
        } else return fin;
    }

    public static String intToRupiah(long amount) {
        String indo = "Rp ";
        return indo + new DecimalFormat("#,##0.#").format(amount);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream streamz = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, streamz);
        byte[] imageByte = streamz.toByteArray();

        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    public static String stringToHexa(String something) {
        return String.format("%040x", new BigInteger(1, something.getBytes(/*YOUR_CHARSET?*/)));
    }

    public static String hexToString(String something) {
        return new String(new BigInteger(something, 16).toByteArray());
    }

    public static String intToHexa(int something) {
        return Integer.toHexString(something);
    }

    public static String DoubleLatLngToHexa(double something) {
        String resultSubstring;
        boolean isMinus;
        String theStringDouble = String.valueOf(something);
        if (String.valueOf(theStringDouble.charAt(0)).equals("-")) {
            resultSubstring = theStringDouble.substring(1);
            isMinus = true;
        } else {
            isMinus = false;
            resultSubstring = theStringDouble;
        }

        @SuppressLint("DefaultLocale")
        String hasilDouble = String.format("%f", Double.parseDouble(resultSubstring) * 60 * 30000);
        DecimalFormat f = new DecimalFormat("#");
        String formattedValue = f.format(Double.parseDouble(hasilDouble));

        Log.d("HasilDouble", "" + Double.parseDouble(resultSubstring) + " = " + formattedValue);

        String hex = Integer.toHexString(Integer.parseInt(formattedValue));
        return ("00000000" + hex).substring(hex.length());
    }
}