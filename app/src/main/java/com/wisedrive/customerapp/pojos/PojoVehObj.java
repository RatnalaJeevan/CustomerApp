package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoVehObj {

    @SerializedName("vehicleId")
    String vehicleId;
    @SerializedName("vehicleNum")
    String vehicleNum;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("dppId")
    String dppId;
    @SerializedName("refNo")
    String refNo;
    @SerializedName("successmsg")
    String successmsg;
    @SerializedName("iswarranty")
    String iswarranty;
    @SerializedName("InspectionButton")
    String InspectionButton;


    public String getInspectionButton() {
        return InspectionButton;
    }

    public String getSuccessmsg() {
        return successmsg;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getIswarranty() {
        return iswarranty;
    }

    public String getRefNo() {
        return refNo;
    }

    public String getDppId() {
        return dppId;
    }



    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }
}
