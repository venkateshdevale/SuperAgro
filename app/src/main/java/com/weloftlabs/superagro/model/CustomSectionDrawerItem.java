package com.weloftlabs.superagro.model;



import android.support.v7.widget.RecyclerView;

import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class CustomSectionDrawerItem extends PrimaryDrawerItem {

    private ColorHolder background;

    public CustomSectionDrawerItem withBackgroundColor(int backgroundColor) {
        this.background = ColorHolder.fromColor(backgroundColor);
        return this;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        super.bindView(holder);

        if (background != null) {
            background.applyToBackground(holder.itemView);
        }
    }
}