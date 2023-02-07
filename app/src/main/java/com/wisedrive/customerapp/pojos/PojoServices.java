package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoServices {

    String serviceId;
    String packageId;

    @SerializedName("activity_name")
    private String activity_name;
    @SerializedName("activity_id")
    private String activity_id;
    @SerializedName("id")
    private String id;
    @SerializedName("package_id")
    private String package_id;
    @SerializedName("created_on")
    private String created_on;
    @SerializedName("is_active")
    private String is_active;

    public PojoServices(String serviceId, String packageId) {
        this.serviceId = serviceId;
        this.packageId = packageId;
    }


    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
