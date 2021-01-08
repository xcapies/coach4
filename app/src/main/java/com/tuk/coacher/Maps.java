package com.tuk.coacher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class Maps extends FragmentActivity implements  View.OnClickListener {
    private static String TAG = "TAG";
    private MaterialButton btn_map_search;
    private TextInputEditText te_map_search;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Maps :: onCreate : ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn_map_search = findViewById(R.id.btn_map_search);
        te_map_search = findViewById(R.id.te_map_search);
        btn_map_search.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
                Log.d(TAG,
                        "Maps :: onRequestPermissionsResult : success : grantResults : " + Arrays.toString(grantResults));
            } else {
                Log.e(TAG,
                        "Maps :: onRequestPermissionsResult : FAILED  : grantResults : " + Arrays.toString(grantResults));
            }
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                MarkerOptions options =
                                        new MarkerOptions().position(latLng).title("You");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng
                                        , 90));
                                googleMap.addMarker(options);
//                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                                googleMap.setTrafficEnabled(false);
//                                googleMap.setIndoorEnabled(false);
//                                googleMap.setBuildingsEnabled(false);
//                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Maps :: onCreate : getLocation : ", e);
                }
            });
        } else {
            ActivityCompat.requestPermissions(Maps.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    44);
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        //        la -1.2921254236781285 lo 36.8246522540283
//        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setTrafficEnabled(true);
//        mMap.setIndoorEnabled(true);
//        mMap.setBuildingsEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        final LatLng kitengela = new LatLng(-1.4940959, 36.945468999999996);
//        mMap.addMarker(new MarkerOptions().position(kitengela).title("Marker in Kitengela"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(kitengela));
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                .target(googleMap.getCameraPosition().target)
//                .zoom(20)
//                .bearing(30)
//                .tilt(45)
//                .build()));
//
//
//    }


    @Override
    public void onClick(View view) {
        if (view == btn_map_search) {
//            destination = te_map_search.getText().toString();
//            destination = destination.replace(" ", "+");
//            mapFragment.getMapAsync(this);
        }
    }
}
