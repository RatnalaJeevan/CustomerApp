package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPopup {

    @SerializedName("buttonName")
    String buttonName;
    @SerializedName("displaymsg")
    String displaymsg;
    @SerializedName("category_name")
    String category_name;
    @SerializedName("bgcolor")
    String bgcolor;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("dpp_id")
    String dpp_id;
    @SerializedName("remaining_amount")
    double remaining_amount;
    @SerializedName("buttoncolor")
    String buttoncolor;
    @SerializedName("textcolor")
    String textcolor;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("status")
    String status;
    @SerializedName("title")
    String title;
    @SerializedName("arrowimg")
    String arrowimg;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("main_package_id")
    String main_package_id;
    @SerializedName("package_type_name_id")
    String package_type_name_id;
    @SerializedName("showAddcarDynamic")
    String showAddcarDynamic;
    @SerializedName("selfinspection")
    String selfinspection;
    @SerializedName("odometerUpdate")
    String odometerUpdate;
    @SerializedName("id")
    String id;

    public String getId() {
        return id;
    }

    public String getOdometerUpdate() {
        return odometerUpdate;
    }

    public String getSelfinspection() {
        return selfinspection;
    }

    public String getShowAddcarDynamic() {
        return showAddcarDynamic;
    }

    public String getPackage_type_name_id() {
        return package_type_name_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getMain_package_id() {
        return main_package_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public double getRemaining_amount() {
        return remaining_amount;
    }

    public String getTitle() {
        return title;
    }

    public String getArrowimg() {
        return arrowimg;
    }

    public String getButtonName() {
        return buttonName;
    }

    public String getDisplaymsg() {
        return displaymsg;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getDpp_id() {
        return dpp_id;
    }

    public String getButtoncolor() {
        return buttoncolor;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getStatus() {
        return status;
    }
}
