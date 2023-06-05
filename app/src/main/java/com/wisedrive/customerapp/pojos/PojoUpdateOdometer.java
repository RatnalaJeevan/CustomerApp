package com.wisedrive.customerapp.pojos;

public class PojoUpdateOdometer {


    String vehId;
    String leadVehId;
    String updatedFrom;
    String customerId;
    String odometer;
    String odometerimg;

    public PojoUpdateOdometer(String vehId, String leadVehId, String updatedFrom, String customerId, String odometer, String odometerimg) {
        this.vehId = vehId;
        this.leadVehId = leadVehId;
        this.updatedFrom = updatedFrom;
        this.customerId = customerId;
        this.odometer = odometer;
        this.odometerimg = odometerimg;
    }
}
