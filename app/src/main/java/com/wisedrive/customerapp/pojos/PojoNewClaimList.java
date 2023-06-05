package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoNewClaimList {

    @SerializedName("status_id")
    String status_id;
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("vehicle_model")
    String vehicle_model;
    @SerializedName("claim_id")
    String claim_id;
    @SerializedName("claim_type_id")
    String claim_type_id;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("claim_name")
    String claim_name;
    @SerializedName("status_name")
    String status_name;
    @SerializedName("requested_on")
    String requested_on;

    public String getRequested_on() {
        return requested_on;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getClaim_name() {
        return claim_name;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getClaim_id() {
        return claim_id;
    }

    public String getClaim_type_id() {
        return claim_type_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }
}
