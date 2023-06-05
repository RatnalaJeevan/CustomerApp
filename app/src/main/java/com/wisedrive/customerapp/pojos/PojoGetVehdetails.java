package com.wisedrive.customerapp.pojos;

import retrofit2.http.Query;

public class PojoGetVehdetails {

    String vehicleNo;
    String leadId;
    String customerId;

    public PojoGetVehdetails(String vehicleNo, String leadId, String customerId) {
        this.vehicleNo = vehicleNo;
        this.leadId = leadId;
        this.customerId = customerId;
    }
}
