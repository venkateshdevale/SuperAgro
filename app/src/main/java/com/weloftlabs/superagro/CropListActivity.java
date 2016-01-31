package com.weloftlabs.superagro;

import android.content.Context;
import android.database.Observable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.weloftlabs.superagro.adapters.CropsListAdapter;
import com.weloftlabs.superagro.interfaces.CropsInterface;
import com.weloftlabs.superagro.models.CropsList;
import com.weloftlabs.superagro.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CropListActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    @Bind(R.id.crops_list_view) RecyclerView cropsListView;
    @Bind(R.id.loading_layout)  RelativeLayout loadingLayout;
    RecyclerView.LayoutManager mLayoutManager;
    final static String TAG = CropListActivity.class.getSimpleName();
    Retrofit retrofit;
    CropsInterface apiService;
    CropsListAdapter mAdapter;
    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this);
        cropsListView.setLayoutManager(mLayoutManager);
        initializeRetrofit();
        if(isNetworkConnected())
        {
            //Send Location
            populateCropsList("a");
            //Call the Api on the Background thread here
        }
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        /*mapFragment.getMapAsync(this);*/
    }

    void populateCropsList(String sortOrder)
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
                /**
                 * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
                 * <p/>
                 * The {@link Observable} will not call this method if it calls {@link #onError}.
                 */
                @Override
                public void onCompleted()
                {
                    Log.d(TAG, "Receive Complete");
                }

                /**
                 * Notifies the Observer that the {@link Observable} has experienced an error condition.
                 * <p/>
                 * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
                 * {@link #onCompleted}.
                 *
                 * @param e the exception encountered by the Observable
                 */
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
