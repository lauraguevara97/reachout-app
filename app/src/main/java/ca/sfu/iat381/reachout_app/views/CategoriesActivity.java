package ca.sfu.iat381.reachout_app.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ca.sfu.iat381.reachout_app.R;
import ca.sfu.iat381.reachout_app.model.Event;
import ca.sfu.iat381.reachout_app.model.EventData;

public class CategoriesActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient locationClient;
    private com.google.android.gms.location.LocationListener mLocationListener;


    private final int LOCATION_PERMISSION = 1;
    private ImageButton sportsCategory;
    private ImageButton outdoorCategory;
    private ImageButton technologyCategory;
    private ImageButton artsCategory;
    private ImageButton foodCategory;
    private ImageButton religionCategory;
    private ImageButton concertsCategory;
    private ImageButton familyCategory;



    List<Event> eventResults;

    public ProgressBar eventLoadingBar;

    private Double currentLatitude;
    private Double currentLongitude;


//    @Override
//    protected void onPause() {
//        super.onPause();
//        LocationServices.FusedLocationApi.removeLocationUpdates(locationClient, mLocationListener);
//    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Toast.makeText(this, "Connected to location services", Toast.LENGTH_SHORT).show();

        mLocationListener = new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {



            }
        };

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(locationClient, request, mLocationListener);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);




        eventResults = new ArrayList<Event>();

        sportsCategory = (ImageButton) findViewById(R.id.sportsBtn);
        outdoorCategory = (ImageButton) findViewById(R.id.outdoorBtn);
        technologyCategory = (ImageButton) findViewById(R.id.technology_btn);
        artsCategory = (ImageButton) findViewById(R.id.artsBtn);
        foodCategory = (ImageButton) findViewById(R.id.foodBtn);
        religionCategory = (ImageButton) findViewById(R.id.religionBtn);
        concertsCategory = (ImageButton) findViewById(R.id.concertsBtn);
        familyCategory = (ImageButton) findViewById(R.id.familyBtn);

        sportsCategory.setOnClickListener(this);
        outdoorCategory.setOnClickListener(this);
        technologyCategory.setOnClickListener(this);
        artsCategory.setOnClickListener(this);
        foodCategory.setOnClickListener(this);
        religionCategory.setOnClickListener(this);
        concertsCategory.setOnClickListener(this);
        familyCategory.setOnClickListener(this);

        locationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationClient.connect();



        eventLoadingBar = (ProgressBar) findViewById(R.id.progress_bar_fetchEvents);


        checkConnection();





        //Fetch all events within sports category
//        sportsCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showCurrentLocation();
//                //Animate loading bar
//                eventLoadingBar.setVisibility(View.VISIBLE);
//
//
//                //Fetch events in background task
//                FetchEventsAsyncTask fetchEvents = new FetchEventsAsyncTask();
//                Log.e("LOCATION", "Latitude" + currentLatitude);
//                Log.e("LOCATION", "Longitude" + currentLongitude);
//                fetchEvents.execute("http://api.eventful.com/json/events/search?...&keywords=Canucks&where="+ currentLatitude + "," + currentLongitude +
//                        "&within=100&units=km&category=sports&app_key=LGZXJ2LkPvTZQghJ&sort_order=date&date=2017033100-2017040200&sort_order=popularity&sort_direction=descending");
//
//            }
//        });

    }


    //Handle event category clicks
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.sportsBtn:


                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);


                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=sports&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

//                Log.e("LOCATION", "Latitude" + currentLatitude);
//                Log.e("LOCATION", "Longitude" + currentLongitude);

                break;
            case R.id.outdoorBtn:


                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=outdoors_recreation&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

                break;

            case R.id.technology_btn:

                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=technology&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");


                break;

            case R.id.artsBtn:

                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=performing_arts&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

                break;
            case R.id.foodBtn:

                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=food&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

                break;

            case R.id.religionBtn:
                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=religion_spirituality&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

                break;
            case R.id.concertsBtn:
                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=music&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=popularity&sort_direction=descending");

                break;


            case R.id.familyBtn:
                showCurrentLocation();
                //Animate loading bar
                eventLoadingBar.setVisibility(View.VISIBLE);

                //Fetch events in background task
                new FetchEventsAsyncTask().execute("http://api.eventful.com/json/events/search?...&where="+ currentLatitude + "," + currentLongitude +
                        "&within=15&units=km&category=family_fun_kids&date=2017040700-2017043000&app_key=LGZXJ2LkPvTZQghJ&sort_order=date&sort_direction=descending");

                break;

            default:
                break;
        }
    }




    public void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(locationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Could not connect!", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            currentLatitude = currentLocation.getLatitude();
            currentLongitude = currentLocation.getLongitude();

            System.out.println(currentLocation.getLatitude());
            System.out.println(currentLocation.getLongitude());
        }


    }



    private class FetchEventsAsyncTask extends AsyncTask<String, Void, List<Event>> {
        @Override
        protected List<Event> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            publishProgress();

            eventResults = EventData.getEventInfo(urls[0]);
            return eventResults;
        }

        @Override
        protected void onPostExecute(List<Event> events) {

            //Go to the event activity and list events of that category
            Intent i = new Intent(CategoriesActivity.this, EventActivity.class);
            i.putExtra("Class", "CategoriesActivity");
            i.putExtra("event_list", (Serializable) eventResults);

            startActivity(i);

            System.out.println("We reach the post execute");
            eventLoadingBar.setVisibility(View.INVISIBLE);



        }
    }

    public void checkConnection(){
        ConnectivityManager connectMgr =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            //fetch data

            String networkType = networkInfo.getTypeName().toString();
            Toast.makeText(this, "connected to " + networkType, Toast.LENGTH_LONG).show();
        }
        else {
            //display error
            Toast.makeText(this, "no network connection", Toast.LENGTH_LONG).show();
        }
    }



}
