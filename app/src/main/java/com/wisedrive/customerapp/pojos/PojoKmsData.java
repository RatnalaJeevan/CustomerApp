package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class PojoKmsData implements Serializable {


    String odometer_image;
    String odometer;
    String odometer_updated_on;


    public String getOdometer_image() {
        return odometer_image;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getOdometer_updated_on() {
        return odometer_updated_on;
    }


}
