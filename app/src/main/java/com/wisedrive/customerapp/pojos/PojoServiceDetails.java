package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoServiceDetails {

    @SerializedName("package_name")
    String package_name;
    @SerializedName("icon_url")
    String icon_url;
    @SerializedName("package_description")
    String package_description;
    @SerializedName("package_id")
    String package_id;

    public String getPackage_name() {
        return package_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getPackage_description() {
        return package_description;
    }

    public String getPackage_id() {
        return package_id;
    }
}
