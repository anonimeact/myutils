package com.anonimeact.docompressimageuri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Didi Yulianto (anonimeact) on 31/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoCompressBitmapUri {
    public static Bitmap SetUp(Context context, Uri picUri) {
        try {
            ExifInterface ei = new ExifInterface(picUri.getPath());
            File file0 = FileUtil.from(context, picUri);
            File file = new ShrinkImg(context)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file0);

            String mCurrentPhotoPath = file.getAbsolutePath();
            Bitmap fix = setPic(mCurrentPhotoPath);
            Bitmap fix1 = DoRotateImageIfRequired(ei, fix);

            //Convert to byte array
            ByteArrayOutputStream streamz = new ByteArrayOutputStream();
            fix1.compress(Bitmap.CompressFormat.PNG, 90, streamz);

            return fix1;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return null;
        }
    }

    public static Bitmap DoRotateImageIfRequired(ExifInterface ei, Bitmap img) {
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private static Bitmap setPic(String uri) {
        // Get the dimensions of the View
        int targetW = 500;
        int targetH = 500;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(uri, bmOptions);
    }

    public static String getBitmapSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
