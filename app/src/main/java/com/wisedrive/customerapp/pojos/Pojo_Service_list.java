package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pojo_Service_list {


    @SerializedName("icon_url")
    String icon_url;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("final_price")
    double final_price;
    @SerializedName("description")
    String description;
    @SerializedName("package_description")
    String package_description;

    @SerializedName("is_expired")
    String is_expired;
    @SerializedName("ServiceIncludes")
    ArrayList<Pojo_Description_lines> ServiceIncludes;

    public boolean isVisible ;

    public String getIs_expired() {
        return is_expired;
    }

    public String getDescription() {
        return description;
    }

    public String getPackage_description() {
        return package_description;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisibility(boolean visibility) {
        this.isVisible = isVisible;

    }

    public double getFinal_price() {
        return final_price;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public ArrayList<Pojo_Description_lines> getServiceIncludes() {
        return ServiceIncludes;
    }
}
