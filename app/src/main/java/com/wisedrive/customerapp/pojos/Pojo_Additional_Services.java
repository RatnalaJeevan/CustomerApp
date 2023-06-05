package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Additional_Services {
    @SerializedName("product_id")
    String product_id;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("description")
    String description;
    @SerializedName("year_id")
    String year_id;
    @SerializedName("validity")
    String validity;
    @SerializedName("product_name")
    String product_name;
    @SerializedName("product_icon")
    String product_icon;
    String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getProduct_icon() {
        return product_icon;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getDescription() {
        return description;
    }

    public String getYear_id() {
        return year_id;
    }

    public String getValidity() {
        return validity;
    }

    public String getProduct_name() {
        return product_name;
    }
}
