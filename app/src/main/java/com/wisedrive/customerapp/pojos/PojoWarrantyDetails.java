package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoWarrantyDetails {

    @SerializedName("vehicle_no")
    private String vehicle_no;
    @SerializedName("expires_on_kms")
    private String expires_on_kms;
    @SerializedName("expires_on_date")
    private String expires_on_date;
    @SerializedName("valid_from")
    private String valid_from;

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getExpires_on_kms() {
        return expires_on_kms;
    }

    public void setExpires_on_kms(String expires_on_kms) {
        this.expires_on_kms = expires_on_kms;
    }

    public String getExpires_on_date() {
        return expires_on_date;
    }

    public void setExpires_on_date(String expires_on_date) {
        this.expires_on_date = expires_on_date;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }
}
