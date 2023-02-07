package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoClaimTypes {
    @SerializedName("claim_type_id")
    String claim_type_id;
    @SerializedName("claim_name")
    String claim_name;

    String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getClaim_type_id() {
        return claim_type_id;
    }

    public void setClaim_type_id(String claim_type_id) {
        this.claim_type_id = claim_type_id;
    }

    public String getClaim_name() {
        return claim_name;
    }

    public void setClaim_name(String claim_name) {
        this.claim_name = claim_name;
    }
}
