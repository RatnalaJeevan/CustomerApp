package com.wisedrive.customerapp.pojos;

public class PojoRequestVehInsp {
    private String leadId;
    private String customerId;
    private String vehicleNo;
    private String phoneNum;
    private String location;
    private String address;
    private String date;
    private String rcBack;
    private String rcFront;
    private String aadharFront;
    private String aadharBack;
    private String insuranceCopy;
    private String time;
    private String pincode;
    private String cityId;
    private String stateId;

    public PojoRequestVehInsp(String leadId, String customerId, String vehicleNo, String phoneNum,
                              String location, String address, String date, String rcBack, String rcFront,
                              String aadharFront, String aadharBack, String insuranceCopy, String time,
                              String pincode, String cityId, String stateId) {
        this.leadId = leadId;
        this.customerId = customerId;
        this.vehicleNo = vehicleNo;
        this.phoneNum = phoneNum;
        this.location = location;
        this.address = address;
        this.date = date;
        this.rcBack = rcBack;
        this.rcFront = rcFront;
        this.aadharFront = aadharFront;
        this.aadharBack = aadharBack;
        this.insuranceCopy = insuranceCopy;
        this.time = time;
        this.pincode = pincode;
        this.cityId = cityId;
        this.stateId = stateId;
    }
}
