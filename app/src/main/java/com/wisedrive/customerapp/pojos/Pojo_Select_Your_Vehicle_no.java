package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Your_Vehicle_no {
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("fuel_type")
    String fuel_type;
    @SerializedName("manufacturing_year")
    String manufacturing_year;
    @SerializedName("odometer")
    String odometer;
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("vehicle_model")
    String vehicle_model;

    public String getFuel_type() {
        return fuel_type;
    }

    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }
}
