package com.weloftlabs.superagro.models;

/**
 * Created by Shabaz on 31-Jan-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("sector_desc")
    @Expose
    private String sectorDesc;
    @SerializedName("group_desc")
    @Expose
    private String groupDesc;
    @SerializedName("commodity_desc")
    @Expose
    private String commodityDesc;
    @SerializedName("state_name")
    @Expose
    private String stateName;

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("location_desc")
    @Expose
    private String locationDesc;
    @SerializedName("reference_period_desc")
    @Expose
    private String referencePeriodDesc;
    @SerializedName("load_time")
    @Expose
    private String loadTime;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     *
     * @return
     * The sectorDesc
     */
    public String getSectorDesc() {
        return sectorDesc;
    }

    /**
     *
     * @param sectorDesc
     * The sector_desc
     */
    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    /**
     *
     * @return
     * The groupDesc
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     *
     * @param groupDesc
     * The group_desc
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     *
     * @return
     * The commodityDesc
     */
    public String getCommodityDesc() {
        return commodityDesc;
    }

    /**
     *
     * @param commodityDesc
     * The commodity_desc
     */
    public void setCommodityDesc(String commodityDesc) {
        this.commodityDesc = commodityDesc;
    }

    /**
     *
     * @return
     * The stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     *
     * @param stateName
     * The state_name
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     *
     * @return
     * The locationDesc
     */
    public String getLocationDesc() {
        return locationDesc;
    }

    /**
     *
     * @param locationDesc
     * The location_desc
     */
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    /**
     *
     * @return
     * The referencePeriodDesc
     */
    public String getReferencePeriodDesc() {
        return referencePeriodDesc;
    }

    /**
     *
     * @param referencePeriodDesc
     * The reference_period_desc
     */
    public void setReferencePeriodDesc(String referencePeriodDesc) {
        this.referencePeriodDesc = referencePeriodDesc;
    }

    /**
     *
     * @return
     * The loadTime
     */
    public String getLoadTime() {
        return loadTime;
    }

    /**
     *
     * @param loadTime
     * The load_time
     */
    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    /**
     *
     * @return
     * The value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}