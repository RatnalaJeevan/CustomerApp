package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class CustomerVehicleDetails {

    @SerializedName("vehicle_id")
    private String vehicle_id;
    @SerializedName("expires_on_kms")
    private String expires_on_kms;
    @SerializedName("valid_from")
    private String valid_from;
    @SerializedName("vehicle_no")
    private String vehicle_no;
    @SerializedName("valid_to")
    private String valid_to;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getExpires_on_kms() {
        return expires_on_kms;
    }

    public void setExpires_on_kms(String expires_on_kms) {
        this.expires_on_kms = expires_on_kms;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }
}
