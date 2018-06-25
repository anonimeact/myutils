package com.anonimeact.dopolyline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anonimeact.dopolyline.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Didi Yulianto (anonimeact) on 27/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoCreateRouteMaps {

    @SuppressLint("StaticFieldLeak")
    private static DoCreateRouteMaps routeMaps;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @SuppressLint("StaticFieldLeak")
    private static View customMarkerView;

    private String color = "#00ace6";
    private static GoogleMap googleMap;
    private static LatLng origins, destinations;

    @SuppressLint("StaticFieldLeak")
    private static ImageView iv_marker_image;
    private int weigtRoute = 5;
    private boolean isPrintPoly = true;
    private boolean isGeodesic = false;
    private boolean isClickable = false;
    private float zIndex = 55;
    private int tilt;
    public static PolylineOptions polylineOptions;

    public static DoCreateRouteMaps setUps(Context cont) {
        routeMaps = new DoCreateRouteMaps();
        context = cont;
        return routeMaps;
    }

    public DoCreateRouteMaps create(LatLng origin, LatLng destination, GoogleMap gMap) {
        googleMap = gMap;
        origins = origin;
        destinations = destination;
        isPrintPoly = true;
        String url_route = getUrl(origin, destination);
        CreateRoute drawRoute = new CreateRoute(googleMap);
        drawRoute.execute(url_route);
        return routeMaps;
    }

    public DoCreateRouteMaps setRouteColor(String customColor) {
        this.color = customColor;
        return this;
    }

    public DoCreateRouteMaps setRouteWeight(int customWeight) {
        this.weigtRoute = customWeight;
        return this;
    }

    public DoCreateRouteMaps isPrintPoly(boolean isPrintPoly) {
        this.isPrintPoly = isPrintPoly;
        return this;
    }

    public DoCreateRouteMaps setGeodesic(boolean isGeodesic) {
        this.isGeodesic = isGeodesic;
        return this;
    }

    public DoCreateRouteMaps setClickable(boolean isClickable) {
        this.isClickable = isClickable;
        return this;
    }

    public DoCreateRouteMaps setGeodesic(float zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    public DoCreateRouteMaps setFromMarkerDefault(String title) {
        googleMap.addMarker(new MarkerOptions()
                .position(origins)
                .title(title));
        return this;
    }

    public DoCreateRouteMaps setFromMarkerCustom(int drawable, final String title, int width) {
        /*
         * Drawable should be .png file
         * */

        createMarker(context, googleMap, origins, drawable, title, width);
        return this;
    }

    public DoCreateRouteMaps setDestinationMarkerDefault(String title) {
        googleMap.addMarker(new MarkerOptions()
                .position(destinations)
                .title(title));
        return this;
    }

    public DoCreateRouteMaps setDestinationMarkerCustom(int drawable, final String title, int width) {
        createMarker(context, googleMap, destinations, drawable, title, width);
        return this;
    }

    public static Marker createMarker(Context conteX, GoogleMap gMap, LatLng latlng, int drawablE, final String titlE, int widtH) {
                /*
         * Drawable should be .png file
         * */
        Marker marker;
        customMarkerView = ((LayoutInflater) conteX.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_custom_marker, null);
        iv_marker_image = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        try {
            FrameLayout.LayoutParams parmsFL = new FrameLayout.LayoutParams(widtH, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_marker_image.setLayoutParams(parmsFL);

            Bitmap bitmap = BitmapFactory.decodeResource(conteX.getResources(),drawablE);
            marker = gMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(titlE)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(customMarkerView, bitmap))));

        } catch (Exception z) {
            LinearLayout.LayoutParams parmsLL = new LinearLayout.LayoutParams(widtH, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_marker_image.setLayoutParams(parmsLL);
            Bitmap bitmap = BitmapFactory.decodeResource(conteX.getResources(),
                    drawablE);
            marker = gMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(titlE)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(customMarkerView, bitmap))));
        }

        return marker;
    }

    public DoCreateRouteMaps setAnimateCameraBoth() {
        AnimateBothMarker(googleMap, origins, destinations);
        return this;
    }

    public DoCreateRouteMaps setAnimateCameraBothCustom(final GoogleMap gMap, final LatLng originS, final LatLng destinationS) {
        AnimateBothMarker(gMap, originS, destinationS);
        return this;
    }

    private void AnimateBothMarker(final GoogleMap gMap, final LatLng originS, final LatLng destinationS){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLngBounds.Builder b = new LatLngBounds.Builder();
                b.include(originS);
                b.include(destinationS);

                LatLngBounds bounds = b.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                gMap.animateCamera(cu);
            }
        }, 500);
    }

    public DoCreateRouteMaps setAnimateCameraOrigin(GoogleMap gMap, LatLng originS, int zoom, int tilt) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(originS)
                .zoom(zoom)
                .tilt(tilt)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));//// Bounds
        return this;
    }

    public DoCreateRouteMaps setAnimateCameraDestination(GoogleMap gMap, LatLng destinationS, int zoom, int tilt) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(destinationS)
                .zoom(zoom)
                .tilt(tilt)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));//// Bounds
        return this;
    }

    private static String getUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private class CreateRoute extends AsyncTask<String, Void, String> {

        private GoogleMap mMap;
        private CreateRoute(GoogleMap mMap) {
            this.mMap = mMap;
        }

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = getJsonRoutePoint(url[0]);
                /*Log.d("Background Task data", data);*/
            } catch (Exception e) {
                /*Log.d("Background Task", e.toString());*/
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            CreateRouteTask routeDrawerTask = new CreateRouteTask(mMap);
            routeDrawerTask.execute(result);
        }

        /**
         * A method to download json data from url
         */
        private String getJsonRoutePoint(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                /*Log.d("getJsonRoutePoint", data.toString());*/
                br.close();

            } catch (Exception e) {
                /*Log.d("Exception", e.toString());*/
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }

    private class CreateRouteTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        private GoogleMap mMap;

        private CreateRouteTask(GoogleMap mMap) {
            this.mMap = mMap;
        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataRouteParser parser = new DataRouteParser();
                // Starts parsing data
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result != null) {
                drawPolyLine(result);

            }
        }

        private void drawPolyLine(List<List<HashMap<String, String>>> result) {
            PolylineOptions lineOptions;
            int routeColor;
            ArrayList<LatLng> points;
            lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(weigtRoute);
                lineOptions.geodesic(isGeodesic);
                lineOptions.clickable(isClickable);
                lineOptions.zIndex(zIndex);
                routeColor = Color.parseColor(color);
                if (routeColor == 0)
                    lineOptions.color(0xFF0A8F08);
                else
                    lineOptions.color(routeColor);
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null && mMap != null) {
                if (isPrintPoly)
                    mMap.addPolyline(lineOptions);

                polylineOptions = lineOptions;
                /*Log.d("onPostExecute", "Polylines NotNull");*/
            }
        }
    }

    private class DataRouteParser {

        /**
         * Receives a JSONObject and returns a list of lists containing latitude and longitude
         */
        private List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jRoutes;
            JSONArray jLegs;
            JSONArray jSteps;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /* Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<>();

                    /* Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        /* Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);
                            /*Log.d("CatekDataPolyline", list.toString());*/

                            /* Traversing all points */
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude));
                                hm.put("lng", Double.toString((list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        /**
         * Method to decode polyline points
         * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
         */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

    private static Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap) {
        iv_marker_image.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;
    }
}
