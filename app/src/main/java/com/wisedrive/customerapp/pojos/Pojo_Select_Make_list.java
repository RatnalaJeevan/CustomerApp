package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Make_list {

    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("id")
    String id;
    @SerializedName("car_brand")
    String car_brand;

    public String getCar_brand() {
        return car_brand;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

    public String getId() {
        return id;
    }
}
