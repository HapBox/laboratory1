package com.mirea.matyash.laboratory1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mirea.matyash.laboratory1.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 100;
    private boolean isWork = false;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String[] PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
            isWork = hasPermissions(this, PERMISSION);
            if (!isWork) {
                ActivityCompat.requestPermissions(this, PERMISSION, REQUEST_CODE_PERMISSION_LOCATION);
            }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng mirea = new LatLng(55.670005, 37.479894);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                mirea).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().title("??????????")
                .snippet("???????????????????? ?????????????????????????????? ??????").position(mirea));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // ???????????????????? ???????????? ?????????????????????? ????????????????????????????
        mMap.setMyLocationEnabled(true);
        // ???????????????????? ???????????? ?????????????????? ????????????????
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // ?????????????????????? ???????? ?????????????????????????? ??????????
        mMap.setTrafficEnabled(true);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().title("?????? ???").snippet("?????????? ??????????").position(latLng));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_LOCATION) {
            // permission granted
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}