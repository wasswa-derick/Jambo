package com.rosen.jambo.views.currentlocation;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rosen.jambo.R;
import com.rosen.jambo.views.articles.MainActivity;


/**
 * Created by Derick W on 27,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class CurrentLocationFragment extends Fragment implements LocationListener, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    Button btnProceed;
    TextView tvAddress;
    TextView tvEmpty;
    RelativeLayout rlPick;

    private static GoogleMap mMap;
    public static Location mLastLocation;
    Marker assetMarker;
    double latitude;
    double longitude;

    public CurrentLocationFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_location, container, false);

        btnProceed = view.findViewById(R.id.btnLocation);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rlPick = view.findViewById(R.id.rlPickLocation);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLastLocation = MainActivity.locationHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();
                    setMarker(mLastLocation);

                } else {

                    if(btnProceed.isEnabled())
                        btnProceed.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Proceed to the next step");
            }
        });

        // check availability of play services
        if (MainActivity.locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            MainActivity.locationHelper.buildGoogleApiClient();
        }

        return view;
    }

    public void showToast(String message)
    {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void getAddress()
    {
        Address locationAddress;

        locationAddress = MainActivity.locationHelper.getAddress(latitude,longitude);

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+="\n"+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;

                tvEmpty.setVisibility(View.GONE);
                tvAddress.setText(currentLocation);
                tvAddress.setVisibility(View.VISIBLE);

                if(!btnProceed.isEnabled())
                    btnProceed.setEnabled(true);
            }

        }
        else
            showToast("Something went wrong");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mSetUpMap();
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * create method to set map view
     */
    public void mSetUpMap() {

        /**clear the map before redraw to them*/
        mMap.clear();

        mLastLocation = MainActivity.locationHelper.getLocation();
        if (mLastLocation != null) {
            onLocationChanged(mLastLocation);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        setMarker(location);
    }

    public void setMarker(Location location){
        double latitudeVal = location.getLatitude();
        double longitudeVal = location.getLongitude();
        LatLng latLng = new LatLng(latitudeVal, longitudeVal);

        if (assetMarker != null) {
            assetMarker.remove();
        }

        assetMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        assetMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        float zoomLevel = 5.0f;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel), 2000, null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
