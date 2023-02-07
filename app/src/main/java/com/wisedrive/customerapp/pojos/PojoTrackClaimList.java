package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoTrackClaimList {
   @SerializedName("status_id")
   String status_id;
    @SerializedName("status_name")
    String status_name;
    @SerializedName("date_1")
    String date_1;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getDate_1() {
        return date_1;
    }

    public void setDate_1(String date_1) {
        this.date_1 = date_1;
    }
}
