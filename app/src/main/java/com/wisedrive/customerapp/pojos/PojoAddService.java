package com.wisedrive.customerapp.pojos;

public class PojoAddService {

    String vehicleId;
    String customerId;
    String packageName;
    String serviceOn;
    String addressId;
    String address;
    String pincode;
    String landmark;
    String location;
    String cityId;
    String city;
    String stateId;
    String state;
    String comment;
    String time;

    public PojoAddService(String vehicleId, String customerId, String packageName, String serviceOn, String addressId,
                          String address, String pincode, String landmark, String location, String cityId, String city,
                          String stateId, String state, String comment, String time) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.packageName = packageName;
        this.serviceOn = serviceOn;
        this.addressId = addressId;
        this.address = address;
        this.pincode = pincode;
        this.landmark = landmark;
        this.location = location;
        this.cityId = cityId;
        this.city = city;
        this.stateId = stateId;
        this.state = state;
        this.comment = comment;
        this.time = time;
    }
}
