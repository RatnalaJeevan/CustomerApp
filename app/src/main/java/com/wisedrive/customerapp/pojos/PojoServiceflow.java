package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoServiceflow {

    @SerializedName("status_id")
    private String status_id;
    @SerializedName("status_name")
    private String status_name;
    @SerializedName("service_date")
    private String service_date;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("comments")
    String comments;
    @SerializedName("service_type")
    String service_type;
    @SerializedName("service_flow_date")
    String service_flow_date;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("id")
    String id;
    @SerializedName("happy_code")
    String happy_code;

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

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getService_flow_date() {
        return service_flow_date;
    }

    public void setService_flow_date(String service_flow_date) {
        this.service_flow_date = service_flow_date;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHappy_code() {
        return happy_code;
    }

    public void setHappy_code(String happy_code) {
        this.happy_code = happy_code;
    }
}
