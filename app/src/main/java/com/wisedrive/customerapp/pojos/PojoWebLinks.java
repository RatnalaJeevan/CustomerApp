package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoWebLinks {
    @SerializedName("privacy_policy")
    String privacy_policy;
    @SerializedName("terms")
    String terms;
    @SerializedName("claim_warranty")
    String claim_warranty;
    @SerializedName("copy_right")
    String copy_right;
    @SerializedName("view_policy")
    String view_policy;

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getClaim_warranty() {
        return claim_warranty;
    }

    public void setClaim_warranty(String claim_warranty) {
        this.claim_warranty = claim_warranty;
    }

    public String getCopy_right() {
        return copy_right;
    }

    public void setCopy_right(String copy_right) {
        this.copy_right = copy_right;
    }

    public String getView_policy() {
        return view_policy;
    }

    public void setView_policy(String view_policy) {
        this.view_policy = view_policy;
    }
}
