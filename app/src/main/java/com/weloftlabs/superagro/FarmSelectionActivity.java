package com.weloftlabs.superagro;

import android.content.Intent;
import android.database.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.weloftlabs.superagro.adapters.CropsListAdapter;
import com.weloftlabs.superagro.interfaces.CropsInterface;
import com.weloftlabs.superagro.ui.MainActivity;

import butterknife.ButterKnife;
import retrofit.Retrofit;
import rx.Subscription;

public class FarmSelectionActivity extends ActionBarActivity  implements OnMapReadyCallback
{

    private GoogleMap mMap;
    RecyclerView.LayoutManager mLayoutManager;
    final static String TAG = FarmSelectionActivity.class.getSimpleName();
    Retrofit retrofit;
    CropsInterface apiService;
    CropsListAdapter mAdapter;
    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_selection);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Select Farm");
        toolbar.setLogo(R.mipmap.ic_launcher);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        Button confirmButton = (Button) findViewById(R.id.confirm_location);
        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(FarmSelectionActivity.this, MainActivity.class));
                FarmSelectionActivity.this.finish();
            }
        });
    }

/*    void populateCropsList(String sortOrder)
    {
        Log.d(TAG,sortOrder);
        if(true)
        {

            Gson mGson = new Gson();
            CropsList cropsList = mGson.fromJson(Constants.cropsList,CropsList.class);
            if (mAdapter == null)
            {
                mAdapter = new CropsListAdapter(cropsList.getCropItem());
                cropsListView.setAdapter(mAdapter);
            } else
            {
                mAdapter.changeDataSet(cropsList.getCropItem());
                mAdapter.notifyDataSetChanged();
                //Reset the scroll to first item
                cropsListView.scrollToPosition(0);
            }
            cropsListView.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
        }
        else
        {
            rx.Observable<CropsList> call = apiService.getCropsList();
            subscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CropsList>()
            {
                *//**
                 * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
                 * <p/>
                 * The {@link Observable} will not call this method if it calls {@link #onError}.
                 *//*
                @Override
                public void onCompleted()
                {
                    Log.d(TAG, "Receive Complete");
                }

                *//**
                 * Notifies the Observer that the {@link Observable} has experienced an error condition.
                 * <p/>
                 * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
                 * {@link #onCompleted}.
                 *
                 * @param e the exception encountered by the Observable
                 *//*
                @Override
                public void onError(Throwable e)
                {
                    Log.d(TAG, "There was a error");
                    loadingLayout.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    ((TextView) loadingLayout.findViewById(R.id.textView)).setText("There was an Error :-(");
                    e.printStackTrace();
                }


                @Override
                public void onNext(CropsList cropsList)
                {
                    if (mAdapter == null)
                    {
                        mAdapter = new CropsListAdapter(cropsList.getCropItem());
                        cropsListView.setAdapter(mAdapter);
                    } else
                    {
                        mAdapter.changeDataSet(cropsList.getCropItem());
                        mAdapter.notifyDataSetChanged();
                        //Reset the scroll to first item
                        cropsListView.scrollToPosition(0);
                    }
                    cropsListView.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                }
            });
        }
    }

    void initializeRetrofit()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(CropsInterface.class);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    protected void onDestroy()
    {
        if(subscription!=null)
            this.subscription.unsubscribe();
        super.onDestroy();
    }*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    Toolbar toolbar;
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        // Add a marker in Sydney and move the camera
        LatLng billsFarm = new LatLng(34.739082, -117.996416);
        mMap.addMarker(new MarkerOptions().position(billsFarm).title("Bill's Farm").visible(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(billsFarm));
        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(34.7417197,-118.0004748),
                        new LatLng(34.741429, -117.991654),
                        new LatLng(34.736263, -117.991756),
                        new LatLng(34.736051, -118.001046));
        rectOptions.fillColor(Color.parseColor("#33FF0000"));
        rectOptions.strokeColor(Color.RED);

// Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);

    }
}
