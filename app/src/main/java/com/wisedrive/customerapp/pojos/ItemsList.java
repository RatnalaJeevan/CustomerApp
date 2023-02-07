package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsList {
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("lostId")
    @Expose
    private String lostId;
    @SerializedName("comment")
    @Expose
    private String comment;



    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getLostId() {
        return lostId;
    }

    public void setLostId(String lostId) {
        this.lostId = lostId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
