package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class
PojoAddOnServices {

    @SerializedName("final_amount")
    String final_amount;
    @SerializedName("part_name")
    String part_name;
    @SerializedName("part_no")
    String part_no;

    public String getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(String final_amount) {
        this.final_amount = final_amount;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }
}
