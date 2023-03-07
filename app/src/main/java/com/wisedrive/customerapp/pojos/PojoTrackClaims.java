package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoTrackClaims {
    @SerializedName("updated_by")
    String updated_by;
    @SerializedName("created_on_date")
    String created_on_date;
    @SerializedName("display_name")
    String display_name;
    @SerializedName("created_on_time")
    String created_on_time;
    @SerializedName("status")
    String status;
    @SerializedName("otp")
    String otp;
    @SerializedName("description")
    String description;
    @SerializedName("status_id")
    String status_id;

    public String getStatus_id() {
        return status_id;
    }

    public String getDescription() {
        return description;
    }

    public String getOtp() {
        return otp;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public String getCreated_on_date() {
        return created_on_date;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getCreated_on_time() {
        return created_on_time;
    }

    public String getStatus() {
        return status;
    }
}
