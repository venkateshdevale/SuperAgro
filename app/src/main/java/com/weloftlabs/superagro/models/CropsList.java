package com.weloftlabs.superagro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CropsList {

    @SerializedName("CropItem")
    @Expose
    private List<CropItem> CropItem = new ArrayList<CropItem>();

    /**
     *
     * @return
     * The CropItem
     */
    public List<CropItem> getCropItem() {
        return CropItem;
    }

    /**
     *
     * @param CropItem
     * The CropItem
     */
    public void setCropItem(List<CropItem> CropItem) {
        this.CropItem = CropItem;
    }

}