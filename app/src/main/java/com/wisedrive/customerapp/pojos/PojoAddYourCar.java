package com.wisedrive.customerapp.pojos;



import java.util.ArrayList;

public class PojoAddYourCar {

    String leadId;
    String vehNo;
    String sellingPrice;
    String odometer;
    String noOfOwner;
    String color;
    String insType;
    String insProvider;
    String policyNo;
    String expDate;
    String customerId;
    String modelId;
    String fuel;
    String manfYear;
    String transType;
    String insCopy;
    String rcFront;
    String rcBack;
    ArrayList<PojoImges> imagesArr;


    public PojoAddYourCar(String leadId, String vehNo, String sellingPrice, String odometer, String noOfOwner,
                          String color, String insType, String insProvider, String policyNo, String expDate,
                          String customerId, String modelId, String fuel, String manfYear, String transType,
                          String insCopy, String rcFront, String rcBack, ArrayList<PojoImges> imagesArr
                          ) {
        this.leadId = leadId;
        this.vehNo = vehNo;
        this.sellingPrice = sellingPrice;
        this.odometer = odometer;
        this.noOfOwner = noOfOwner;
        this.color = color;
        this.insType = insType;
        this.insProvider = insProvider;
        this.policyNo = policyNo;
        this.expDate = expDate;
        this.customerId = customerId;
        this.modelId = modelId;
        this.fuel = fuel;
        this.manfYear = manfYear;
        this.transType = transType;
        this.insCopy = insCopy;
        this.rcFront = rcFront;
        this.rcBack = rcBack;
        this.imagesArr = imagesArr;
    }
}
