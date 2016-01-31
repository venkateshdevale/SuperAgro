package com.weloftlabs.superagro.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.ui.CropStateYearAnalysisActivity;
import com.weloftlabs.superagro.model.Crop;
import com.weloftlabs.superagro.util.GlobalConstant;

import java.util.List;

/**
 * Created by akashs on 10/21/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CropViewHolder> {
    List<Crop> cropList;
    Activity mActivity;

    private int lastPosition = -1;

    public RVAdapter(List<Crop> cropList, Activity activity) {
        this.cropList = cropList;
        this.mActivity = activity;
    }

    @Override
    public CropViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_card, parent, false);
        CropViewHolder pvh = new CropViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CropViewHolder holder, final int position) {
        Crop crop = cropList.get(position);
        holder.cropNameTextView.setText(crop.getCropName());
        holder.stateNameTextView.setText(crop.getStateName() + " (" + crop.getYear() + ")");
        holder.cropValueTextView.setText(String.valueOf(crop.getValue() + "  " + crop.getUnits()));
        if (crop.getCropName().equals("CORN")) {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_corn);
        } else if (crop.getCropName().equals("RICE")) {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_rice);
        } else if (crop.getCropName().equals("WHEAT")) {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_wheat);
        } else if (crop.getCropName().equals("PEANUTS")) {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_peanuts);
        } else if (crop.getCropName().equals("SOYBEANS")) {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_soybeans);
        } else {
            holder.thumbnail.setBackgroundResource(R.drawable.crop_common);
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mActivity, CropStateYearAnalysisActivity.class);
                in.putExtra(GlobalConstant.TAG_commodity_desc, cropList.get(position).getCropName());
                in.putExtra(GlobalConstant.TAG_state_name, cropList.get(position).getStateName());
                in.putExtra(GlobalConstant.TAG_statisticcat_desc, cropList.get(position).getStatisticCategory());
                mActivity.startActivity(in);
            }
        });
        setAnimation(holder.cv, position);
    }

    @Override
    public int getItemCount() {
        if (cropList != null) {
            return cropList.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CropViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView cropNameTextView;
        TextView cropValueTextView;
        TextView stateNameTextView;
        LinearLayout thumbnail;

        CropViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            thumbnail = (LinearLayout) itemView.findViewById(R.id.thumbnail);
            cropNameTextView = (TextView) itemView.findViewById(R.id.crop_name);
            stateNameTextView = (TextView) itemView.findViewById(R.id.state_name);
            cropValueTextView = (TextView) itemView.findViewById(R.id.crop_value);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


}
