package com.wisedrive.customerapp.pojos;

public class PojoBookService {

    private String vehicleId;
    private String customerId;
    private String serPackId;
    private String serviceId;
    private String statusId;
    private String serviceOn;
    private String addressId;
    private String address;
    private String pincode;
    private String landmark;
    private String location;
    private String cityId;
    private String city;
    private String stateId;
    private String state;
    private String comment;
    private String time;
    private String packId;
    private String packType;

    public PojoBookService(String vehicleId, String customerId, String serPackId, String serviceId,
                           String statusId, String serviceOn, String addressId, String address,
                           String pincode, String landmark, String location, String cityId, String city,
                           String stateId, String state, String comment, String time, String packId, String packType) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.serPackId = serPackId;
        this.serviceId = serviceId;
        this.statusId = statusId;
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
        this.packId = packId;
        this.packType = packType;
    }
}
