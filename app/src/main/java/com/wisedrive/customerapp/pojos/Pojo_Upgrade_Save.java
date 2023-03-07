package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Upgrade_Save {

    @SerializedName("product_name")
    String product_name;
    @SerializedName("description")
    String description;
    @SerializedName("validity")
    String validity;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("package_description")
    String package_description;
    @SerializedName("msg")
    String msg;
    @SerializedName("upselling_package_description")
    String upselling_package_description;
    @SerializedName("package_logo")
    String package_logo;
    @SerializedName("final_price")
    double final_price;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("main_package_id")
    String main_package_id;
    @SerializedName("icon_url")
    String icon_url;

    public String getIcon_url() {
        return icon_url;
    }

    public String getMsg() {
        return msg;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getMain_package_id() {
        return main_package_id;
    }

    public String getUpselling_package_description() {
        return upselling_package_description;
    }

    public String getPackage_logo() {
        return package_logo;
    }

    public double getFinal_price() {
        return final_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getDescription() {
        return description;
    }

    public String getValidity() {
        return validity;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_description() {
        return package_description;
    }
}
