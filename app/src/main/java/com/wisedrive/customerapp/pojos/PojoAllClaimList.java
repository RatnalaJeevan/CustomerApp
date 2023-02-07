package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAllClaimList {

    @SerializedName("breakdown_date")
    String breakdown_date;
    @SerializedName("breakdown_place")
    String breakdown_place;
    @SerializedName("car_model")
    String car_model;
    @SerializedName("claim_type_id")
    String claim_type_id;
    @SerializedName("created_on")
    String created_on;
    @SerializedName("status_name")
    String status_name;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("requested_on")
    String requested_on;
    @SerializedName("claim_id")
    String claim_id;
    @SerializedName("claim_code")
    String claim_code;

    @SerializedName("car_brand")
    String car_brand;
    @SerializedName("claim_type")
    String claim_type;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("claim_status_id")
    String claim_status_id;

    public String getClaim_code() {
        return claim_code;
    }

    public String getBreakdown_date() {
        return breakdown_date;
    }

    public String getBreakdown_place() {
        return breakdown_place;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getClaim_type_id() {
        return claim_type_id;
    }

    public void setClaim_type_id(String claim_type_id) {
        this.claim_type_id = claim_type_id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getRequested_on() {
        return requested_on;
    }

    public void setRequested_on(String requested_on) {
        this.requested_on = requested_on;
    }

    public String getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(String claim_id) {
        this.claim_id = claim_id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getClaim_type() {
        return claim_type;
    }

    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getClaim_status_id() {
        return claim_status_id;
    }

    public void setClaim_status_id(String claim_status_id) {
        this.claim_status_id = claim_status_id;
    }
}
