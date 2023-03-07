package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Your_Vehicle_no {
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("vehicle_id")
    String vehicle_id;

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }
}
