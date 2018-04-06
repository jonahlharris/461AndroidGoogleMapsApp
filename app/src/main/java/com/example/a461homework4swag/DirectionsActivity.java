package com.example.a461homework4swag;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class DirectionsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.directions);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        String destination_string = intent.getStringExtra("destination");
        String origin_string = intent.getStringExtra("origin");

        GeoApiContext geo = new GeoApiContext().setApiKey(getString(R.string.google_maps_key));
        DateTime now = new DateTime();
        DirectionsResult directions = null;
        try {
            directions = DirectionsApi.newRequest(geo).mode(TravelMode.DRIVING).origin(origin_string).destination(destination_string).departureTime(now).await();
            LatLng startCoords = new LatLng(directions.routes[0].legs[0].startLocation.lat, directions.routes[0].legs[0].startLocation.lng);
            mMap.addMarker(new MarkerOptions().position(startCoords).title(directions.routes[0].legs[0].startAddress));
            LatLng endCoords = new LatLng(directions.routes[0].legs[0].endLocation.lat, directions.routes[0].legs[0].endLocation.lng);
            mMap.addMarker(new MarkerOptions().position(endCoords).title(directions.routes[0].legs[0].endAddress).snippet(getEndLocationTitle(directions)));
            List<LatLng> decodedPath = PolyUtil.decode(directions.routes[0].overviewPolyline.getEncodedPath());
            mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
            LatLng middle = new LatLng((startCoords.latitude + endCoords.latitude) / 2, (startCoords.longitude + endCoords.longitude) / 2);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(middle));
            System.out.println ("Snippet: " + getEndLocationTitle(directions));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void zoomIn(View view) {
        mMap.moveCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOut(View view) {
        mMap.moveCamera(CameraUpdateFactory.zoomOut());
    }

    private String getEndLocationTitle(DirectionsResult results){
        return  "Time :"+ results.routes[0].legs[0].duration.humanReadable +
                " Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

}
