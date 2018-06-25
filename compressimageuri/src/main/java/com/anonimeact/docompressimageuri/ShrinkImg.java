package com.anonimeact.docompressimageuri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class ShrinkImg {
    private int maxWidth = 240, maxHeight = 240;
    private int quality = 75;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private String destinationPath;

    ShrinkImg(Context context) {
        destinationPath = context.getCacheDir().getPath() + File.pathSeparator + "Compressor";
    }

    ShrinkImg setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationPath = destinationDirectoryPath;
        return this;
    }

    File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    private File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality,
                destinationPath + File.separator + compressedFileName);
    }

    private static File compressImage(File imageFile, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat, int quality, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            decodeBitmapFromFile(imageFile, reqWidth, reqHeight)
                    .compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

    private static Bitmap decodeBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
