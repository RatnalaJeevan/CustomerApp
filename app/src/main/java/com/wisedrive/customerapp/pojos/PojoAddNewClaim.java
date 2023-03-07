package com.wisedrive.customerapp.pojos;

import java.util.ArrayList;

public class PojoAddNewClaim {

    String vehicleId;
    String customerId;
    String claimTypeId;
    String breakdownPlace;
    String date;
    String comment;
    String vehLocation;
    String odometer;
    String employeeId;
    ArrayList<PojoAnswerDetails> symptomArr;
    ArrayList<PojoAddedImages> imagesArr;

    public PojoAddNewClaim(String vehicleId, String customerId, String claimTypeId, String breakdownPlace,
                           String date, String comment, String vehLocation, String odometer, String employeeId,
                           ArrayList<PojoAnswerDetails> symptomArr, ArrayList<PojoAddedImages> imagesArr) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.claimTypeId = claimTypeId;
        this.breakdownPlace = breakdownPlace;
        this.date = date;
        this.comment = comment;
        this.vehLocation = vehLocation;
        this.odometer = odometer;
        this.employeeId = employeeId;
        this.symptomArr = symptomArr;
        this.imagesArr = imagesArr;
    }
}
