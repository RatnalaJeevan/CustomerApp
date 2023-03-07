package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class
Pojo_Class_Mycar {

    @SerializedName("image")
    String image;
    @SerializedName("model_id")
    String model_id;
    @SerializedName("lead_veicle_id")
    String lead_veicle_id;
    @SerializedName("manufacturing_year")
    String manufacturing_year;
    @SerializedName("transmission_type")
    String transmission_type;
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("odometer")
    String odometer;
    @SerializedName("color")
    String color;
    @SerializedName("vehicle_model")
    String vehicle_model;
    @SerializedName("image_type_id")
    String image_type_id;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("fuel_type")
    String fuel_type;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("count")
    String count;
    @SerializedName("packExist")
    String packExist;

    public String getPackExist() {
        return packExist;
    }

    public String getCount() {
        return count;
    }

    public String getImage() {
        return image;
    }

    public String getModel_id() {
        return model_id;
    }

    public String getLead_veicle_id() {
        return lead_veicle_id;
    }

    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getColor() {
        return color;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getImage_type_id() {
        return image_type_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }
}
