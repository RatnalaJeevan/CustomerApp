package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Model {

    @SerializedName("model_id")
    String model_id;
    @SerializedName("car_model")
    String car_model;

    public String getModel_id() {
        return model_id;
    }

    public String getCar_model() {
        return car_model;
    }
}
