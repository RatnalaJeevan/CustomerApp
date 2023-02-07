package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoServiceStatus {
    @SerializedName("service_completed_date")
    String service_completed_date;
    @SerializedName("postpaid_service_count")
    String postpaid_service_count;
    @SerializedName("prepaid_service_count")
    String prepaid_service_count;

    public String getService_completed_date() {
        return service_completed_date;
    }

    public void setService_completed_date(String service_completed_date) {
        this.service_completed_date = service_completed_date;
    }

    public String getPostpaid_service_count() {
        return postpaid_service_count;
    }

    public void setPostpaid_service_count(String postpaid_service_count) {
        this.postpaid_service_count = postpaid_service_count;
    }

    public String getPrepaid_service_count() {
        return prepaid_service_count;
    }

    public void setPrepaid_service_count(String prepaid_service_count) {
        this.prepaid_service_count = prepaid_service_count;
    }
}
