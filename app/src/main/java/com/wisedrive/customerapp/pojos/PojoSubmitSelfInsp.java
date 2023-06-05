package com.wisedrive.customerapp.pojos;

import java.util.ArrayList;

public class PojoSubmitSelfInsp {

    String customerId;
    String vehicleId;
    String leadId;
    String leadVehId;
    String winsvehId;
    String addedBy;
    String color;
    String odometervalue;
    ArrayList<PojoInsImgs> addimagesArr;
    ArrayList<PojoInsVide> addvideosArr;
    ArrayList<PojoInspAns> quesanswinspectionArr;

    public PojoSubmitSelfInsp(String customerId, String vehicleId, String leadId, String leadVehId,
                              String winsvehId, String addedBy, String color, String odometervalue,
                              ArrayList<PojoInsImgs> addimagesArr, ArrayList<PojoInsVide> addvideosArr,
                              ArrayList<PojoInspAns> quesanswinspectionArr) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.leadId = leadId;
        this.leadVehId = leadVehId;
        this.winsvehId = winsvehId;
        this.addedBy = addedBy;
        this.color = color;
        this.odometervalue = odometervalue;
        this.addimagesArr = addimagesArr;
        this.addvideosArr = addvideosArr;
        this.quesanswinspectionArr = quesanswinspectionArr;
    }
}
