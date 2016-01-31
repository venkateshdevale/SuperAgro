package com.weloftlabs.superagro.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.weloftlabs.superagro.CropDetailActivity;
import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.models.CropItem;
import com.weloftlabs.superagro.util.AppController;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shabaz on 30-Jan-16.
 */
public class CropsListAdapter extends RecyclerView.Adapter<CropsListAdapter.CropViewHolder> implements View.OnClickListener
{
    final String TAG = CropsListAdapter.class.getSimpleName();
    List<CropItem> cropList;

    public CropsListAdapter(List<CropItem> cropList)
    {

        this.cropList = cropList;
        Log.d(TAG,cropList.toString());
    }

    public void changeDataSet(List<CropItem> cropList)
    {
        this.cropList = cropList;
    }

    @Override
    public CropViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crops, parent, false);
        CropViewHolder cropViewHolder = new CropViewHolder(v); //create a ViewHolder and pass it
        v.setOnClickListener(this);
        return cropViewHolder;
    }

    @Override
    public void onBindViewHolder(CropViewHolder holder, int position)
    {
        CropItem crop = cropList.get(position);
        holder.cropName.setText(crop.getCropName());
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(Float.parseFloat(crop.getProfit()), 0));
        yValues.add(new BarEntry(3000f, 1));

        BarDataSet set = new BarDataSet(yValues, "Profit");


        set.setColors(new int[] { AppController.context.getResources().getColor(R.color.primary_green), AppController.context.getResources().getColor(R.color.transparent)});
        String[] xVals = new String[]{"$",""};
        BarData data = new BarData(xVals, set);
        holder.mChart.setData(data);
        holder.mChart.invalidate();
        //mLog.d("Poster Query = "+ Constants.POSTER_BASE+crop.getPosterPath());

    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return cropList.size();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
// RecyclerView Does not have a onItemClickListener , so had to have onclickListener for each and every item
    @Override
    public void onClick(View v)
    {
        int itemPosition = ((RecyclerView) v.getParent()).getChildLayoutPosition(v);
        Intent mIntent = new Intent(v.getContext(), CropDetailActivity.class);
        mIntent.putExtra("DETAILS", cropList.get(itemPosition).getCropName());
        v.getContext().startActivity(mIntent);
        //Toast.makeText(v.getContext(), "clicked "+itemPosition, Toast.LENGTH_SHORT).show();
    }

    public static class CropViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.crop_name)
        TextView cropName;
        @Bind(R.id.bar_chart)
        HorizontalBarChart mChart;

        public CropViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mChart.setDrawGridBackground(false);
            mChart.setDescription("");

            // scaling can now only be done on x- and y-axis separately
            mChart.setPinchZoom(false);

            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);
            mChart.getAxisLeft().setEnabled(false);
            mChart.getAxisRight().setStartAtZero(false);
            mChart.getAxisRight().setTextSize(9f);
            mChart.animateY(2500);
            YAxis yAxis = mChart.getAxisRight();
            yAxis.setDrawGridLines(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setDrawLabels(false);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setTextSize(0f);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
            l.setFormSize(0f);
            l.setFormToTextSpace(0f);
            l.setXEntrySpace(0f);
        }


    }
}

