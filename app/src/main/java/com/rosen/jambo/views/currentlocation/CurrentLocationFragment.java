package com.rosen.jambo.views.currentlocation;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rosen.jambo.databinding.CurrentLocationBinding;
import com.rosen.jambo.utils.NetworkConnectionDetector;
import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.articles.ArticleDetails;
import com.rosen.jambo.views.articles.ArticleListClick;
import com.rosen.jambo.views.articles.ArticleViewModelFactory;
import com.rosen.jambo.views.articles.ArticlesAdapter;
import com.rosen.jambo.views.articles.ArticlesViewModel;
import com.rosen.jambo.views.articles.FragmentModel;
import com.rosen.jambo.views.articles.MainActivity;

import com.rosen.jambo.R;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by Derick W on 27,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class CurrentLocationFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    Button btnProceed;
    TextView tvAddress;
    TextView tvEmpty;
    RelativeLayout rlPick;
    LinearLayout listRoot, header;
    CardView mapCard;

    RecyclerView articlesRecyclerView;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    ArticlesAdapter articlesAdapter;
    List<Article> articleList = new ArrayList<>();
    ArticlesViewModel articlesViewModel;

    private static GoogleMap mMap;
    Marker assetMarker;
    double latitude;
    double longitude;
    String city;


    //  Broadcast event for internet connectivity
    BroadcastReceiver networkStateReceiver;

    CurrentLocationBinding binding;

    public CurrentLocationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        articlesViewModel = ViewModelProviders.of(this, new ArticleViewModelFactory(requireActivity().getApplication())).get(ArticlesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.current_location, container, false);
        View view = binding.getRoot();

        binding.setViewModel(articlesViewModel);

        btnProceed = view.findViewById(R.id.btnLocation);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rlPick = view.findViewById(R.id.rlPickLocation);
        mapCard = view.findViewById(R.id.card_view);
        listRoot = view.findViewById(R.id.root);
        header = view.findViewById(R.id.header);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        initialiseArticleList(view);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.lat != 0.0) {
                    String address = "";
                    latitude = MainActivity.lat;
                    longitude = MainActivity.lon;
                    Location location = new Location("");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    address = MainActivity.locationHelper.getAddress(latitude, longitude) != null ? MainActivity.locationHelper.getAddress(latitude, longitude).getAddressLine(0) : "You are here";
                    setMarker(location, address);
                    getAddress(location);

                } else {

                    if (btnProceed.isEnabled())
                        btnProceed.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city != null) {
                    header.setVisibility(View.GONE);
                    btnProceed.setVisibility(View.GONE);
                    mapCard.setVisibility(View.GONE);
                    listRoot.setVisibility(View.VISIBLE);
                    showToast("News articles for " + city);
                    requireActivity().setTitle(city);

                    checkNetworkConnection();

                }
            }
        });

        return view;
    }

    public void getOfflineArticles(String articleTag){
        if (!articleList.isEmpty()) {
            articleList.clear();
        }
        articleList.addAll(articlesViewModel.getOfflineArticlesByTag(articleTag));
        articlesAdapter.notifyDataSetChanged();
    }

    private void checkNetworkConnection() {
        // Network Utility
        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean connection = new NetworkConnectionDetector(requireActivity().getApplicationContext()).InternetConnectionStatus();
                if (!connection) {
                    if (articleList.isEmpty()) {
                        getOfflineArticles(requireActivity().getTitle().toString());
                    }
                } else {
                    getArticlesForCurrentLocation(city);
                }
            }
        };
        requireActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_quilt) {
            articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else if (id == R.id.action_list) {
            articlesRecyclerView.setLayoutManager(linearLayoutManager);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialiseArticleList(View view) {
        articlesRecyclerView = view.findViewById(R.id.articles_list);

        articlesAdapter = new ArticlesAdapter(requireActivity(), articleList);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        articlesRecyclerView.addOnItemTouchListener(new ArticleListClick(requireActivity(), new ArticleListClick.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                Intent intent = new Intent(requireActivity(), ArticleDetails.class);
                Bundle bundle = new Bundle();

                Article article = articleList.get(position);
                bundle.putString("title", article.getTitle());
                bundle.putString("content", article.getContent());
                bundle.putString("description", article.getDescription());
                bundle.putString("author", article.getAuthor());
                bundle.putString("timestamp", article.getPublishedAt());
                bundle.putString("url", article.getUrl());
                bundle.putString("image", article.getUrlToImage());
                intent.putExtra("data", bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongPress(View childView, int position) {

            }
        }));
    }

    private void getArticlesForCurrentLocation (String currentLocation) {
        articlesViewModel.getAllNewsArticles(currentLocation, requireActivity().getResources().getString(R.string.news_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        if (!articleList.isEmpty()) {
                            articleList.clear();
                        }

                        articleList.addAll(articles);
                        articlesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        articlesAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void showToast(String message)
    {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void getAddress(Location location)
    {
        Address locationAddress;

        locationAddress = MainActivity.locationHelper.getAddress(location.getLatitude(), location.getLongitude());

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            city = locationAddress.getLocality();
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

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MainActivity.PERMISSION_LOCATION_REQUEST_CODE);
            return;
        }

        int off = 0;
        try {
            off = Settings.Secure.getInt(requireActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (off == 0) {
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);
        }

        mMap.setMyLocationEnabled(true);
    }

    public void setMarker(Location location, String address){
        double latitudeVal = location.getLatitude();
        double longitudeVal = location.getLongitude();
        LatLng latLng = new LatLng(latitudeVal, longitudeVal);

        if (assetMarker != null) {
            assetMarker.remove();
        }

        assetMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        assetMarker.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
