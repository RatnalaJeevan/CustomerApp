package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Select_Date {
    @SerializedName("date")
    String date;
    @SerializedName("display_date")
    String display_date;
    String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getDate() {
        return date;
    }

    public String getDisplay_date() {
        return display_date;
    }
}
