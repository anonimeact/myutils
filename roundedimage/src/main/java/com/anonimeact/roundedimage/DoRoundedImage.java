package com.anonimeact.roundedimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DoRoundedImage {
    public static void exeDrawable(Context context, ImageView imageView, int drawablez, float round) {
        Bitmap raw = BitmapFactory.decodeResource(context.getResources(), drawablez);

        int width = raw.getWidth();
        int height = raw.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        SetRound(raw, result, width, height, round);

        imageView.setImageBitmap(result);
    }

    public static void exeUrl(ImageView imageView, String url, float round) {
        new DownLoadImageTask(imageView, round).execute(url);
    }

    private static class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private float rounds;

        public DownLoadImageTask(ImageView imageView, float round) {
            this.imageView = imageView;
            this.rounds = round;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap raw) {
            int width = raw.getWidth();
            int height = raw.getHeight();
            Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            SetRound(raw, result, width, height, rounds);

            imageView.setImageBitmap(result);
        }
    }

    private static void SetRound(Bitmap raw, Bitmap result, int width, int height, float round){
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(raw, rect, rect, paint);
    }
}
