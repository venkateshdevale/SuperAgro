package com.weloftlabs.superagro.model;


public class Crop {

    private String cropName;
    private String stateName;
    private String year;
    private String value;
    private String statisticCategory;
    private String units;

    public Crop(String cropName, String stateName, String year, String value, String statisticCategory, String units) {
        this.cropName = cropName;
        this.stateName = stateName;
        this.year = year;
        this.value = value;
        this.statisticCategory = statisticCategory;
        this.units = units;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatisticCategory() {
        return statisticCategory;
    }

    public void setStatisticCategory(String statisticCategory) {
        this.statisticCategory = statisticCategory;
    }
}
