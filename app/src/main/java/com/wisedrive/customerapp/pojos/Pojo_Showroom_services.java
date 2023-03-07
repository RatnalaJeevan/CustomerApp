package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Showroom_services {

    @SerializedName("package_name")
    String package_name;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("icon_url")
    String icon_url;
    @SerializedName("status_id")
    String status_id;
    @SerializedName("status_name")
    String status_name;
    @SerializedName("service_id")
    String service_id;
    @SerializedName("package_id")
    String package_id;

    @SerializedName("description")
    String description;

    public String getDescription() {
        return description;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getService_id() {
        return service_id;
    }

    public String getPackage_id() {
        return package_id;
    }
}
