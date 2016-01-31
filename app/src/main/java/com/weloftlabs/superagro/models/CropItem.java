package com.weloftlabs.superagro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shabaz on 30-Jan-16.
 */

public class CropItem {

    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("profit")
    @Expose
    private String profit;

    /**
     *
     * @return
     * The cropName
     */
    public String getCropName() {
        return cropName;
    }

    /**
     *
     * @param cropName
     * The cropName
     */
    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    /**
     *
     * @return
     * The profit
     */
    public String getProfit() {
        return profit;
    }

    /**
     *
     * @param profit
     * The profit
     */
    public void setProfit(String profit) {
        this.profit = profit;
    }

}