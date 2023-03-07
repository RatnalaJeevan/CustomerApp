package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoBankList {
    @SerializedName("id")
    private String id;
    @SerializedName("insurance_provider")
    private String insurance_provider;

    public String getId() {
        return id;
    }

    public String getInsurance_provider() {
        return insurance_provider;
    }
}
