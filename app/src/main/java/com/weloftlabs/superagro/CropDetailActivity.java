package com.weloftlabs.superagro;

import android.database.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.weloftlabs.superagro.interfaces.CropDetailInterface;
import com.weloftlabs.superagro.models.CropDetails;
import com.weloftlabs.superagro.models.Datum;
import com.weloftlabs.superagro.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CropDetailActivity extends AppCompatActivity
{
    final String TAG = CropDetailActivity.class.getSimpleName();
    Retrofit retrofit;
    CropDetailInterface apiService;
    private Subscription subscription;
    @Bind(R.id.bar_chart)
    BarChart mBarChart;
    @Bind(R.id.line_chart)
    LineChart mLineChart;
    @Bind(R.id.combined_chart)
    CombinedChart mCombinedChart;
    private int[] lineChartData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);
        ButterKnife.bind(this);
        //((TextView)findViewById(R.id.crop_name)).setText(getIntent().getStringExtra("DETAILS"));
        initializeRetrofit();
        populateCropsYield("RICE","CALIFORNIA");
        populateCropsPrice("RICE","CALIFORNIA");
        populateCropsAreaPlanted("RICE","CALIFORNIA");

    }
    void populateCropsYield(String crop,String state)
    {
        Log.d(TAG,crop+" "+state);

            rx.Observable<CropDetails> call = apiService.getCropDetails(crop,state,"YIELD");
            subscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CropDetails>()
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
                    //((TextView) findViewById(R.id.crop_name)).setText("There was an Error :-(");
                    e.printStackTrace();
                }


                @Override
                public void onNext(CropDetails cropsDetails)
                {
                    Log.d(TAG,"Hurray YIELD");
                    //((TextView) findViewById(R.id.crop_name)).setText(cropsDetails.getData().toString());
                    Log.d(TAG,"RAW DATA "+cropsDetails.getData().toString());
                    int[] yieldPerYear = new int[20];
                    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;
                        yieldPerYear[year-1995]+=Integer.parseInt(d.getValue().replace(",",""));
                        Log.d(TAG,year+" "+yieldPerYear[year-1995]);
                        /*yearCount[year-1995]++;
                        if(yield == null)
                        {
                            yieldPerYear.put(d.getYear(),Integer.parseInt(d.getValue().replace(",","")));
                        }
                        else
                            yieldPerYear.put(d.getYear(),yield+Integer.parseInt(d.getValue().replace(",","")));
                   */ }
                    Log.d(TAG,"BEFORE Averaging"+yieldPerYear.toString());
                    createBarChart();
                    setBarChartData(yieldPerYear);
                /*    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;

                        int yield = yieldPerYear.get(d.getYear());
                        Log.d("TAG","Yield = "+yield);
                        Log.d("TAG","Year = "+(year-1995)+"");
                        Log.d("TAG","Repeat = "+yearCount[year-1995]);
                        yield= yield/yearCount[year-1995];
                        yieldPerYear.put(d.getYear(),yield);
                    }
                    Log.d(TAG,"AFTER Averaging"+yieldPerYear.toString());*/
                  /*  if (mAdapter == null)
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
                    loadingLayout.setVisibility(View.GONE);*/
                }
            });
    }
    void populateCropsPrice(String crop,String state)
    {
        Log.d(TAG,crop+" "+state);

        rx.Observable<CropDetails> call = apiService.getCropDetails(crop,state,"PRICE RECEIVED");
        subscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CropDetails>()
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
                //((TextView) findViewById(R.id.crop_name)).setText("There was an Error :-(");
                e.printStackTrace();
            }


            @Override
            public void onNext(CropDetails cropsDetails)
            {
                Log.d(TAG, "Hurray PRICE RECEIVED");
                //((TextView) findViewById(R.id.crop_name)).setText(cropsDetails.getData().toString());
                Log.d(TAG, "RAW DATA " + cropsDetails.getData().toString());
                float[] pricePerYear = new float[20];
                for (Datum d : cropsDetails.getData())
                {
                    int year = Integer.parseInt(d.getYear());
                    if (year < 1995) continue;
                    pricePerYear[year - 1995] += Float.parseFloat(d.getValue().replace(",", ""));
                    Log.d(TAG, year + " " + pricePerYear[year - 1995]);
                        /*yearCount[year-1995]++;
                        if(yield == null)
                        {
                            yieldPerYear.put(d.getYear(),Integer.parseInt(d.getValue().replace(",","")));
                        }
                        else
                            yieldPerYear.put(d.getYear(),yield+Integer.parseInt(d.getValue().replace(",","")));
                   */
                }
                Log.d(TAG, "BEFORE Averaging" + pricePerYear.toString());
                createLineChart();
                setLineChartData(pricePerYear);
                /*    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;

                        int yield = yieldPerYear.get(d.getYear());
                        Log.d("TAG","Yield = "+yield);
                        Log.d("TAG","Year = "+(year-1995)+"");
                        Log.d("TAG","Repeat = "+yearCount[year-1995]);
                        yield= yield/yearCount[year-1995];
                        yieldPerYear.put(d.getYear(),yield);
                    }
                    Log.d(TAG,"AFTER Averaging"+yieldPerYear.toString());*/
                  /*  if (mAdapter == null)
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
                    loadingLayout.setVisibility(View.GONE);*/
            }
        });
    }

    private void createLineChart()
    {
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(false);

        mLineChart.setDrawGridBackground(false);


        XAxis x = mLineChart.getXAxis();
        x.setEnabled(false);

       mLineChart.getAxisLeft().setDrawLabels(false);

        mLineChart.getAxisRight().setEnabled(false);


        mLineChart.getLegend().setEnabled(false);

        mLineChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
    }

    int[] areaPlanted= new int[20];
    void populateCropsAreaPlanted(String crop,String state)
    {
        Log.d(TAG,crop+" "+state);

            rx.Observable<CropDetails> call = apiService.getCropDetails(crop,state,"AREA HARVESTED");
            subscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CropDetails>()
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
                    //((TextView) findViewById(R.id.crop_name)).setText("There was an Error :-(");
                    e.printStackTrace();
                }


                @Override
                public void onNext(CropDetails cropsDetails)
                {
                    Log.d(TAG,"Hurray AREA PLANTED");
                    //((TextView) findViewById(R.id.crop_name)).setText(cropsDetails.getData().toString());
                    Log.d(TAG,"RAW DATA "+cropsDetails.getData().toString());

                    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;
                        areaPlanted[year-1995]+=Integer.parseInt(d.getValue().replace(",",""));
                        Log.d(TAG,year+" "+areaPlanted[year-1995]);
                        /*yearCount[year-1995]++;
                        if(yield == null)
                        {
                            yieldPerYear.put(d.getYear(),Integer.parseInt(d.getValue().replace(",","")));
                        }
                        else
                            yieldPerYear.put(d.getYear(),yield+Integer.parseInt(d.getValue().replace(",","")));
                   */ }
                    Log.d(TAG,"BEFORE Averaging"+areaPlanted.toString());
                    createCombinedChart();
                    populateCropsAreaHarvested("RICE","CALIFORNIA",areaPlanted);
                    //Area harvest will use this data
                /*    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;

                        int yield = yieldPerYear.get(d.getYear());
                        Log.d("TAG","Yield = "+yield);
                        Log.d("TAG","Year = "+(year-1995)+"");
                        Log.d("TAG","Repeat = "+yearCount[year-1995]);
                        yield= yield/yearCount[year-1995];
                        yieldPerYear.put(d.getYear(),yield);
                    }
                    Log.d(TAG,"AFTER Averaging"+yieldPerYear.toString());*/
                  /*  if (mAdapter == null)
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
                    loadingLayout.setVisibility(View.GONE);*/
                }
            });
    }

    private void createCombinedChart()
    {
        mCombinedChart.setDescription("");
        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawGridBackground(false);
        mCombinedChart.setDrawBarShadow(false);

        // draw bars behind lines
        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[] {
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });
        mCombinedChart.getAxisLeft().setDrawLabels(false);

        mCombinedChart.getAxisRight().setEnabled(false);

       /* YAxis rightAxis = mCombinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mCombinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);*/

        XAxis xAxis = mCombinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);


//        data.setData(generateBubbleData());
//         data.setData(generateScatterData());
//         data.setData(generateCandleData());

        
    }
    public void setCombinedChartData(int[] lineData,int[] barData)
    {
        List<String> years = new ArrayList<>();
        for(int i=0;i<lineData.length;i++)
            years.add((1995+i)+"");
        CombinedData data = new CombinedData(years);

        data.setData(generateLineData(lineData));
        data.setData(generateBarData(barData));
        mCombinedChart.setData(data);
        mCombinedChart.invalidate();
        mCombinedChart.setVisibleXRangeMaximum(6);
        mCombinedChart.moveViewToX(lineData.length-1);
    }
    private LineData generateLineData(int[] lineChartData) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        int itemcount = lineChartData.length;
        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(lineChartData[index], index));

        LineDataSet set = new LineDataSet(entries, "Area Harvested");
        set.setColor(Color.BLUE);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLUE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData(int[] areaPlanted) {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        int itemcount = areaPlanted.length;
        for (int index = 0; index < itemcount; index++)
            entries.add(new BarEntry(areaPlanted[index], index));

        BarDataSet set = new BarDataSet(entries, "Area Planted");
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }
    void populateCropsAreaHarvested(String crop, String state, final int[] plantedData)
    {
        Log.d(TAG,crop+" "+state);

            rx.Observable<CropDetails> call = apiService.getCropDetails(crop,state,"AREA PLANTED");
            subscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CropDetails>()
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
                    //((TextView) findViewById(R.id.crop_name)).setText("There was an Error :-(");
                    e.printStackTrace();
                }


                @Override
                public void onNext(CropDetails cropsDetails)
                {
                    Log.d(TAG,"Hurray AREA HARVESTED");
                    //((TextView) findViewById(R.id.crop_name)).setText(cropsDetails.getData().toString());
                    Log.d(TAG,"RAW DATA "+cropsDetails.getData().toString());
                    int[] harvestedData = new int[20];
                    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;
                        harvestedData[year-1995]+=Integer.parseInt(d.getValue().replace(",",""));
                        Log.d(TAG,year+" "+harvestedData[year-1995]);
                        /*yearCount[year-1995]++;
                        if(yield == null)
                        {
                            yieldPerYear.put(d.getYear(),Integer.parseInt(d.getValue().replace(",","")));
                        }
                        else
                            yieldPerYear.put(d.getYear(),yield+Integer.parseInt(d.getValue().replace(",","")));
                   */ }
                    Log.d(TAG,"BEFORE Averaging"+harvestedData.toString());
                    setCombinedChartData(harvestedData,plantedData);
                /*    for (Datum d:cropsDetails.getData())
                    {
                        int year = Integer.parseInt(d.getYear());
                        if(year<1995)
                            continue;

                        int yield = yieldPerYear.get(d.getYear());
                        Log.d("TAG","Yield = "+yield);
                        Log.d("TAG","Year = "+(year-1995)+"");
                        Log.d("TAG","Repeat = "+yearCount[year-1995]);
                        yield= yield/yearCount[year-1995];
                        yieldPerYear.put(d.getYear(),yield);
                    }
                    Log.d(TAG,"AFTER Averaging"+yieldPerYear.toString());*/
                  /*  if (mAdapter == null)
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
                    loadingLayout.setVisibility(View.GONE);*/
                }
            });
    }

    private void setBarChartData(int[] yieldData)
    {
        ArrayList<String> xVals = new ArrayList<String>();
        int count = yieldData.length;
        for (int i = 0; i < count; i++) {
            xVals.add((1995+i)+"");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (yieldData[i]);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Years");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mBarChart.setData(data);
        mBarChart.invalidate();
        mBarChart.setVisibleXRangeMaximum(6);
        mBarChart.moveViewToX(count-1);
    }

    private void createBarChart()
    {
        //mBarChart.setOnChartValueSelectedListener(this);

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawHighlightArrow(true);
        mBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(true);
        mBarChart.animateY(2000);
        mBarChart.setDrawGridBackground(false);
        // mBarChart.setDrawYLabels(false);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


         mBarChart.getAxisLeft().setDrawLabels(false);
         mBarChart.getAxisRight().setDrawLabels(false);


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
        apiService = retrofit.create(CropDetailInterface.class);
    }
    @Override
    protected void onDestroy()
    {
        if(subscription!=null)
            this.subscription.unsubscribe();
        super.onDestroy();
    }

    public void setLineChartData(float[] lineChartData)
    {
        int count = lineChartData.length;
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((1995 +i) + "");
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            vals1.add(new Entry(lineChartData[i], i));
        }
        LineDataSet set = new LineDataSet(vals1, "Price Received");
        set.setColor(Color.BLUE);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(true);

        set.setCubicIntensity(0.2f);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLUE);
        // create a dataset and give it a type
      /*  LineDataSet set1 = new LineDataSet(vals1, "Price Received");
        set1.setDrawCubic(true);
        //set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.RED);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.BLUE);
        set1.setFillColor(Color.WHITE);
        //set1.setFillAlpha(100);
        set1.setDrawHorizontalHighlightIndicator(true);
        *//*set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });*/

        // create a data object with the datasets
        LineData data = new LineData(xVals, set);
        data.setValueTextSize(9f);
        data.setDrawValues(true);

        // set data
        mLineChart.setData(data);
        mLineChart.invalidate();
    }


}
