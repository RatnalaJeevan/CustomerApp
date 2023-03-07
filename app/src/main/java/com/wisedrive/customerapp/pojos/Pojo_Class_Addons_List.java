package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Class_Addons_List {

    @SerializedName("package_discription")
    String package_discription;
    @SerializedName("amount_saved")
    double amount_saved;
    @SerializedName("percentage_amount_saved")
    String percentage_amount_saved;
    @SerializedName("plan_validity")
    String plan_validity;
    @SerializedName("package_logo")
    String package_logo;
    @SerializedName("actual_price")
    String actual_price;
    @SerializedName("sub_pack_id")
    String sub_pack_id;
    @SerializedName("final_price")
    double final_price;
    @SerializedName("addon_id")
    String addon_id;
    @SerializedName("addon_name")
    String addon_name;
    @SerializedName("display_name")
    String display_name;
    @SerializedName("main_pack_id")
    String main_pack_id;

    @SerializedName("is_recommended")
    String is_recommended;

    String isSelected="n";

    public String getPlan_validity() {
        return plan_validity;
    }

    public String getIs_recommended() {
        return is_recommended;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getPackage_discription() {
        return package_discription;
    }

    public String getPercentage_amount_saved() {
        return percentage_amount_saved;
    }

    public double getAmount_saved() {
        return amount_saved;
    }

    public String getPackage_logo() {
        return package_logo;
    }

    public String getActual_price() {
        return actual_price;
    }

    public String getSub_pack_id() {
        return sub_pack_id;
    }

    public double getFinal_price() {
        return final_price;
    }

    public String getAddon_id() {
        return addon_id;
    }

    public String getAddon_name() {
        return addon_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getMain_pack_id() {
        return main_pack_id;
    }
}
