package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPackageList {
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("package_type")
    private String package_type;
    @SerializedName("status_id")
    private String status_id;
    @SerializedName("icon_url")
    private String icon_url;
    @SerializedName("status_name")
    private String status_name;
    @SerializedName("package_id")
    private String package_id;
    @SerializedName("service_id")
    private String service_id;



    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
}
