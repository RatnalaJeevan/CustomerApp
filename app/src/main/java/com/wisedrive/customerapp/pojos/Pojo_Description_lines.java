package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Description_lines {

    @SerializedName("activity_name")
    String activity_name;
    @SerializedName("activity_id")
    String activity_id;

    public String getActivity_name() {
        return activity_name;
    }

    public String getActivity_id() {
        return activity_id;
    }
}
