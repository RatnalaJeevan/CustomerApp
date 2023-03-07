package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Claim_Type_New_Cus_App {
    @SerializedName("claim_type_id")
    String claim_type_id;
    @SerializedName("claim_name")
    String claim_name;

    public String getClaim_type_id() {
        return claim_type_id;
    }

    public String getClaim_name() {
        return claim_name;
    }
}
