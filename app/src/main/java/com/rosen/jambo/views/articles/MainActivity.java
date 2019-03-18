package com.rosen.jambo.views.articles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.location.aravind.getlocation.GeoLocator;
import com.rosen.jambo.R;
import com.rosen.jambo.utils.NetworkConnectionDetector;
import com.rosen.jambo.views.currentlocation.CurrentLocationFragment;
import com.rosen.jambo.views.currentlocation.LocationHelper;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;

import java.util.ArrayList;
import java.util.Arrays;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener, MyTracker.ADLocationListener {

    protected GoogleApiClient mGoogleApiClient;

    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    Snackbar snackbar;
    private ArrayList<String> mTitles = new ArrayList<>();

    String NEWS_API_KEY = System.getenv("NEWS_API_KEY");


    public static LocationHelper locationHelper;
    public static int PERMISSION_LOCATION_REQUEST_CODE = 1;

    //  Broadcast event for internet connectivity
    BroadcastReceiver networkStateReceiver;

    public static double lat = 0.0;
    public static double lon = 0.0;
    public static GeoLocator geoLocator;
    public static ADLocation locator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snackbar = Snackbar.make(findViewById(R.id.drawer), R.string.no_internet, Snackbar.LENGTH_INDEFINITE);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.locations)));
        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        locationHelper = new LocationHelper(this);

        // check availability of play services
        if (locationHelper.checkPlayServices()) {
            // Building the GoogleApi client
            locationHelper.checkPermission();
        }

        // Show main fragment in container
        goToFragment(new MainFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));

        mViewHolder.mDuoMenuView.setFooterView(R.layout.footer);
        mViewHolder.mDuoMenuView.setHeaderView(R.layout.header);

        findLoc();

        checkNetworkConnection();
    }

    private void findLoc(){
        new MyTracker(getApplicationContext(),this).track();
    }

    public static void setCurrentLocationLatitude(double latitude) {
        lat = latitude;
    }

    public static void setCurrentLocationLongitude(double longitude) {
        lon = longitude;
    }

    private void checkNetworkConnection() {
        // Network Utility
        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean connection = new NetworkConnectionDetector(getApplicationContext()).InternetConnectionStatus();
                if (!connection) {
                    //dismissProgressDialog();

                    snackbar.setAction(R.string.connect, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                                    startActivity(settingsIntent);
                                }
                            }).show();

                } else {
                    snackbar.dismiss();
                }
            }
        };
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    @Override
    public void onHeaderClicked() {

    }

    public void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0:
                //Load articles for Kampala
                goToFragment(new MainFragment(), true);
                break;
            case 1:
                //load articles for Nairobi
                goToFragment(new MainFragment(), true);
                break;
            case 2:
                //Load articles for Lagos
                goToFragment(new MainFragment(), true);
                break;
            case 3:
                //Load articles for Kigali
                goToFragment(new MainFragment(), true);
                break;
            case 4:
                //Load articles for New York
                goToFragment(new MainFragment(), true);
                break;
            case 5:
                goToFragment(new CurrentLocationFragment(), true);
                break;
            default:
                goToFragment(new MainFragment(), false);
                setTitle(0);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    @Override
    public void whereIAM(ADLocation geoLocator) {
        locator = geoLocator;
        setCurrentLocationLatitude(geoLocator.lat);
        setCurrentLocationLongitude(geoLocator.longi);
    }


    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
        //  register broadcast receive once activity is in the foreground
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        // disconnect expensive broadcast receiver
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();

    }

}
