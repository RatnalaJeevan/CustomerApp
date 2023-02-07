package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoFeedbackList {
    @SerializedName("name")
    String name;
    @SerializedName("id")
    String id;
        String serviceId;
        String feedbackId;
        String comment;
        String rating;

    public PojoFeedbackList(String serviceId, String feedbackId, String comment, String rating) {
        this.serviceId = serviceId;
        this.feedbackId = feedbackId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
