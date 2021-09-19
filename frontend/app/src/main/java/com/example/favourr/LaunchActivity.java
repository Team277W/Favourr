package com.example.favourr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.favourr.models.UserContainerModel;
import com.example.favourr.network.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    Double latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        shp = getSharedPreferences("LaunchPrefs", MODE_PRIVATE);
        boolean inAlready = shp.getBoolean("in", false);
        if (inAlready) {
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_launch);
            Button goHome = findViewById(R.id.goHome);
            EditText userName = findViewById(R.id.UserName);
            EditText name = findViewById(R.id.Name);

            goHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = gcd.getFromLocation(latitudeTextView,
                                longitTextView, 1);
                        String cityName = addresses.get(0).getAddressLine(0);
                        System.out.println(cityName.split(" ")[3]);
                        intent.putExtra("Username", userName.getText());
                        intent.putExtra("Name", name.getText());
                        intent.putExtra("City", cityName.toLowerCase());
                        shpEditor = shp.edit();
                        shpEditor.putBoolean("in", true);
                        shpEditor.putString("Username", userName.getText().toString());
                        shpEditor.putString("Name", name.getText().toString());
                        shpEditor.putString("City", cityName.toLowerCase());
                        shpEditor.apply();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("userName", userName.getText().toString());
                        String strRequestBody = new Gson().toJson(params);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), strRequestBody);
                        Call<UserContainerModel> apiInterface = ApiInterface.Companion.create().postUser(requestBody);
                        apiInterface.enqueue(new Callback<UserContainerModel>() {
                            @Override
                            public void onResponse(Call<UserContainerModel> call, Response<UserContainerModel> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Couldn't make user", Toast.LENGTH_LONG).show();
                                } else {
                                    shpEditor = shp.edit();
                                    shpEditor.putString("userId", response.body().getUser().get_id());
                                    shpEditor.apply();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserContainerModel> call, Throwable t) {
                                Log.d("Testing", "Couldn't make user");
                            }
                        });
                        startActivity(intent);
                    } catch (Exception e) {
                        System.out.println(e);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Username", userName.getText().toString());
                        intent.putExtra("Name", name.getText().toString());
                        intent.putExtra("City", "waterloo");
                        shpEditor = shp.edit();
                        shpEditor.putBoolean("in", true);
                        shpEditor.putString("Username", userName.getText().toString());
                        shpEditor.putString("Name", name.getText().toString());
                        shpEditor.putString("City", "waterloo");
                        shpEditor.apply();
                        startActivity(intent);
                    }
                }
            });
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            // method to get the location
            getLastLocation();
        }
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView = location.getLatitude();
                            longitTextView = location.getLongitude();
                            System.out.println("AAAA");
                            System.out.println(latitudeTextView);
                            System.out.println("AAAA");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView = mLastLocation.getLatitude();
            longitTextView = mLastLocation.getLongitude();
            System.out.println("AAAA");
            System.out.println(latitudeTextView);
            System.out.println("AAAA");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            getLastLocation();
        }
    }
}