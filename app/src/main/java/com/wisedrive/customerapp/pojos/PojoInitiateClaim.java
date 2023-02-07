package com.wisedrive.customerapp.pojos;

import java.util.ArrayList;

public class PojoInitiateClaim {

    String vehicleId;
    String customerId;
    String claimTypeId;
    String breakdownPlace;
    String date;
    String comment;
    ArrayList<PojoSelectedSymptoms> SymptomArr;

    public PojoInitiateClaim(String vehicleId, String customerId, String claimTypeId, String breakdownPlace,
                             String date, String comment, ArrayList<PojoSelectedSymptoms> symptomArr) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.claimTypeId = claimTypeId;
        this.breakdownPlace = breakdownPlace;
        this.date = date;
        this.comment = comment;
        this.SymptomArr = symptomArr;
    }
}
