package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Date {
    @SerializedName("date")
    String date;
    @SerializedName("display_date")
    String display_date;
    public String getDate() {
        return date;
    }

    public String getDisplay_date() {
        return display_date;
    }
}
