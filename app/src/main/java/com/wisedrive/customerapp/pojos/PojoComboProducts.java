package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoComboProducts {

    @SerializedName("description")
    String description;
    @SerializedName("validity")
    String validity;
    @SerializedName("package_id")
    String package_id;

    @SerializedName("product_id")
    String product_id;
    @SerializedName("product_name")
    String product_name;
    @SerializedName("year_id")
    String year_id;

    public String getDescription() {
        return description;
    }

    public String getValidity() {
        return validity;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getYear_id() {
        return year_id;
    }
}
