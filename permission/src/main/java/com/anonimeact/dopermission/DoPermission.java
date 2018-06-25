package com.anonimeact.dopermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.regex.Pattern;

/**
 * Created by Didi Yulianto (anonimeact) on 12/05/16.
 * author email didiyuliantos@gmail.com
 */
public class DoPermission {

    /**
     * Do not forget to add
     * uses_permission at manifest
     */

    public static boolean check(final Context context, final String permission, final int reqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {

                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(context);
                        builder.setPositiveButton("GO TO SETTING", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openSettingsApp(context);
                            }
                        });

                        builder.setTitle("Denied");
                        String[] dataArray = permission.split(Pattern.quote("."));
                        builder.setMessage("Permission for " + dataArray[2] + " was denied. " +
                                "Please allow that permission to use our fiture.");
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, reqCode);
                    }
                    return false;
                } else {
                    return true;
                }

            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean checkMultiple(final Context context, String[] permissions, int reqCode) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            boolean checkManual = true, wasFiled = false;
            for (String itemPermission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, itemPermission) != PackageManager.PERMISSION_GRANTED) {
                    checkManual = false;
                }
            }

            if (!checkManual) {
                for (String itemPermission : permissions) {
                    if (!wasFiled) {
                        wasFiled = true;
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, itemPermission)) {

                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(context);
                            builder.setPositiveButton("GO TO SETTING", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    openSettingsApp(context);
                                }
                            });

                            builder.setTitle("Permission Denied");
                            builder.setMessage("Permission was denied. " +
                                    "Please allow all permission in this Apps. to use our fiture. " +
                                    "Go to Setting --> App permission --> Check all permission.");
                            builder.show();
                        } else
                            ActivityCompat.requestPermissions((Activity) context, permissions, reqCode);
                    }
                }
            }

            return false;
        } else {
            return true;
        }
    }

    private static void openSettingsApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }
}
