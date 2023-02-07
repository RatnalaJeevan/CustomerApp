package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPolicyDetails {

    @SerializedName("content")
    String content;
    @SerializedName("image_url")
    String image_url;
    @SerializedName("policy_name")
    String policy_name;
    @SerializedName("id")
    String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
