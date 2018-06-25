package com.anonimeact.mylibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anonimeact.docompressimageuri.DoCompressBitmapUri;
import com.anonimeact.donativemask.Blind;
import com.anonimeact.donativemask.Database;
import com.anonimeact.dopermission.DoPermission;
import com.anonimeact.dopermission.Permissions;
import com.anonimeact.dotakeimage.CropImageView;
import com.anonimeact.dotakeimage.DoPickImage;
import com.anonimeact.mylibrary.R;
import com.anonimeact.roundedimage.DoRoundedImage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static com.anonimeact.dotakeimage.DoPickImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.anonimeact.dotakeimage.DoPickImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
import static com.anonimeact.dotakeimage.DoPickImage.getActivityResult;

/**
 * Created by Didi Yulianto on 22/02/2018.
 */

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ImageView iv_test;
    private TextView text1, text2;
    private int speed = 0, lewatUp, lewatDown;
    private ArrayList<Integer> averrageUp, averrageDown, averrageFit;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        final TextView text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        iv_test = findViewById(R.id.iv_test);

        averrageUp = new ArrayList<>();
        averrageDown = new ArrayList<>();
        averrageFit = new ArrayList<>();

        text.setText(Blind.eKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQ3LCJ1c2VyTmFtZSI6IlBpY2sgUG9pbnQiLCJ1c2VyRW1haWwiOiJwaWNrcG9pbnQubWVAZ21haWwuY29tIiwidXNlclBob25lIjoiMDg4ODg4ODg4OCIsImlhdCI6MTUxOTI3MzIyMSwiZXhwIjoxNTI3OTEzMjIxfQ.DxkcUD1BQsBx3bmEM5nIVCm-YJmZfmPbaYR9g46dz-8"));
        text1.setText(Blind.dKey(text.getText().toString()));

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.onLocationChanged(null);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoPickImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(MainActivity.this);
            }
        });

        String param[] = {Permissions.CAMERA, Permissions.READ_EXTERNAL_STORAGE, Permissions.WRITE_EXTERNAL_STORAGE};
        DoPermission.checkMultiple(this, param, 10);

        /**
         * Testing Rounded Image
         */
        // Drawable Image
        DoRoundedImage.exeDrawable(this, iv_test, R.drawable.jerapah, 10);

        // Url Image
        DoRoundedImage.exeUrl(iv_test, "https://asset.kompas.com/crop/0x0:1000x667/750x500/data/photo/2017/09/16/2548005054.JPG", 10);


        /**
         * Testing Rest
         */
/*//    String url = "http://demo.pickpoint.me:8880/login/auth";
        String url = " https://ntris.tris.solutions:2000/auth/session/local";

        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("email", "didiyuliantos@gmail.com");
        additionalParams.put("password", "zzzzzz");
        additionalParams.put("role", "MTOLL");

        Map<String, String> additionalHeader = new HashMap<>();
        additionalHeader.put("Content-Type", "application/x-www-form-urlencoded");

//        DoRequest.setup(this, url)
//                .exe(Request.Method.POST, new ServiceCallback() {
//                            @Override
//                            public void onSuccess(String result) {
//                                Log.d("CekData", "CekData Success " + result);
//                            }
//
//                            @Override
//                            public void onFailure(String result) {
//                                Log.d("CekData", "CekData Failure " + result);
//                            }
//                        }
//                );

//        DoRequest.setup(this, url)
//                .exeParam(Request.Method.POST, additionalParams, new ServiceCallback() {
//                            @Override
//                            public void onSuccess(String result) {
//                                Log.d("CekData", "CekData Success " + result);
//                            }
//
//                            @Override
//                            public void onFailure(String result) {
//                                Log.d("CekData", "CekData Failure " + result);
//                            }
//                        }
//                );

//        DoRequest.setup(this, url)
//                .exeHeader(Request.Method.POST, additionalHeader, new ServiceCallback() {
//                            @Override
//                            public void onSuccess(String result) {
//                                Log.d("CekData", "CekData Success " + result);
//                            }
//
//                            @Override
//                            public void onFailure(String result) {
//                                Log.d("CekData", "CekData Failure " + result);
//                            }
//                        }
//                );

        DoRequest.setup(this, url)
                .exe(Request.Method.POST, additionalHeader, additionalParams, new ServiceCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("CekData", "CekData Success " + result);
                            }

                            @Override
                            public void onFailure(String result) {
                                Log.d("CekData", "CekData Failure " + result);
                            }
                        }
                );*/

        Database.saveByGroup(this, "DataString", "YourKey", "GroupName");
        Log.d("CakDatanya", "CakData " + Database.getByGroup(this, "YourKey", "GroupName"));

        Database.removeDataCustom(this, "GroupName");


        text1.setText("Kecepatan 1: " + speed + " km/h");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                text1.setText("Kecepatan 1: " + speed + " km/h");

                Log.d("Kecepatan", "Kecepatan hit " + speed);

                if (speed < 60) {
                    // less fast
                    averrageDown.add(speed);
                    if (averrageDown.size() == 10) {
                        if (averrageDown.get(8) < 60 && averrageDown.get(8) < 60) {
                            // notify driver
                            Log.d("Kecepatan", "Kecepatan Down " + speed);
                            averrageDown.clear();
                        } else {
                            averrageDown.clear();
                        }
                    }
                } else if (speed > 60) {
                    if (speed < 100) {
                        // On way
                        averrageFit.add(speed);
                        if (averrageFit.size() > 2) {
                            averrageUp.clear();
                            averrageDown.clear();
                            averrageDown.clear();

                            Log.d("Kecepatan", "Kecepatan Fit " + speed);
                        }
                    } else {
                        // too fast
                        averrageUp.add(speed);
                        if (averrageUp.size() == 10) {
                            if (averrageUp.get(8) > 100 && averrageUp.get(8) > 100) {
                                // notify driver
                                Log.d("Kecepatan", "Kecepatan UP " + speed);

                                averrageUp.clear();
                            } else {
                                averrageDown.clear();
                            }
                        }
                    }
                }

                handler.postDelayed(this, 2000);
            }
        }, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            DoPickImage.ActivityResult result = getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap newBit = DoCompressBitmapUri.SetUp(this, resultUri);
                iv_test.setImageBitmap(newBit);
            } else if (resultCode == CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        DoPermission.check(this, Permissions.ACCESS_FINE_LOCATION, 10);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            text1.setText("0 Km/h");
        } else {
            // Methode pertama
            speed = (int) ((location.getSpeed() * 3600) / 1000);

            // Methode kedua
//            if (location.hasSpeed()) {
//                text2.setText("Kecepatan 2: " + String.valueOf((int) roundDecimal(convertSpeed(location.getSpeed()), 2)) + " km/h");
//            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    int HOUR_MULTIPLIER = 3600;
    double UNIT_MULTIPLIERS = 0.001;

    private double convertSpeed(double speed) {
        return ((speed * HOUR_MULTIPLIER) * UNIT_MULTIPLIERS);
    }

    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();

        return value;
    }
}