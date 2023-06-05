package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoEWPRc {

    @SerializedName("recommendation")
    String recommendation;

    public String getRecommendation() {
        return recommendation;
    }
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("no_of_cars")
    String no_of_cars;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getNo_of_cars() {
        return no_of_cars;
    }
}
