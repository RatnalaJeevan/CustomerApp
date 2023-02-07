package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseModel {


    //get app update details
    @SerializedName("appUpdateDetails")
    PojoAppUpdateDetails appUpdateDetails;

    public PojoAppUpdateDetails getAppUpdateDetails() {
        return appUpdateDetails;
    }

    public void setAppUpdateDetails(PojoAppUpdateDetails appUpdateDetails) {
        this.appUpdateDetails = appUpdateDetails;
    }

    //logindetails
    @SerializedName("logindetails")
    private CustomerLoginDetails logindetails;
    @SerializedName("credentials")
    private CustomerLoginDetails credentials;

    public CustomerLoginDetails getCredentials() {
        return credentials;
    }

    public void setCredentials(CustomerLoginDetails credentials) {
        this.credentials = credentials;
    }

    public CustomerLoginDetails getLogindetails() {
        return logindetails;
    }

    public void setLogindetails(CustomerLoginDetails logindetails) {
        this.logindetails = logindetails;
    }

    //
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //verify activation code details
    @SerializedName("codedetails")
    private CustomerVehicleDetails codedetails;

    public CustomerVehicleDetails getCodedetails() {
        return codedetails;
    }

    public void setCodedetails(CustomerVehicleDetails codedetails) {
        this.codedetails = codedetails;
    }

    //vehicleList
    @SerializedName("vehicleList")
    private ArrayList<PojoVehDetails> vehicleList;

    public ArrayList<PojoVehDetails> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(ArrayList<PojoVehDetails> vehicleList) {
        this.vehicleList = vehicleList;
    }

    //get warranty details,package list
    @SerializedName("warrantydetails")
    private PojoWarrantyDetails warrantydetails;

    public PojoWarrantyDetails getWarrantydetails() {
        return warrantydetails;
    }

    public void setWarrantydetails(PojoWarrantyDetails warrantydetails) {
        this.warrantydetails = warrantydetails;
    }

    @SerializedName("packagelist")
    private ArrayList<PojoPackageList> packagelist;

    public ArrayList<PojoPackageList> getPackagelist() {
        return packagelist;
    }

    public void setPackagelist(ArrayList<PojoPackageList> packagelist) {
        this.packagelist = packagelist;
    }

    //get address list
    @SerializedName("addressList")
    private ArrayList<PojoAddresses> addressList;

    public ArrayList<PojoAddresses> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<PojoAddresses> addressList) {
        this.addressList = addressList;
    }
    //get pincode details

    @SerializedName("getpincodedata")
    private PojoAddresses getpincodedata;

    public PojoAddresses getGetpincodedata() {
        return getpincodedata;
    }

    public void setGetpincodedata(PojoAddresses getpincodedata) {
        this.getpincodedata = getpincodedata;
    }

    //getservicecentre details
    @SerializedName("serviceCentreDetails")
    private PojoNearestServiceCentreDetails serviceCentreDetails;

    public PojoNearestServiceCentreDetails getServiceCentreDetails() {
        return serviceCentreDetails;
    }

    public void setServiceCentreDetails(PojoNearestServiceCentreDetails serviceCentreDetails) {
        this.serviceCentreDetails = serviceCentreDetails;
    }

    //getservivedetails
    @SerializedName("getservingdetails")
    private PojoNearestServiceCentreDetails getservingdetails;

    public PojoNearestServiceCentreDetails getGetservingdetails() {
        return getservingdetails;
    }

    public void setGetservingdetails(PojoNearestServiceCentreDetails getservingdetails) {
        this.getservingdetails = getservingdetails;
    }

    //serviceincludes
    @SerializedName("serviceincludes")
    private ArrayList<PojoServices> serviceincludes;

    public ArrayList<PojoServices> getServiceincludes() {
        return serviceincludes;
    }

    public void setServiceincludes(ArrayList<PojoServices> serviceincludes) {
        this.serviceincludes = serviceincludes;
    }

    //serviceflow([{}])
    @SerializedName("serviceflow")
    private ArrayList<PojoServiceflow> serviceflow;

    public ArrayList<PojoServiceflow> getServiceflow() {
        return serviceflow;
    }

    public void setServiceflow(ArrayList<PojoServiceflow> serviceflow) {
        this.serviceflow = serviceflow;
    }

    //gethappycode{}
    @SerializedName("gethappycode")
    private PojoHappyCode gethappycode;

    public PojoHappyCode getGethappycode() {
        return gethappycode;
    }

    public void setGethappycode(PojoHappyCode gethappycode) {
        this.gethappycode = gethappycode;
    }

    //getsupportdetails{}
    @SerializedName("supportdetails")
    private PojoSupportDetails supportdetails;

    public PojoSupportDetails getSupportdetails() {
        return supportdetails;
    }

    public void setSupportdetails(PojoSupportDetails supportdetails) {
        this.supportdetails = supportdetails;
    }

    //getServicecompletriondate
    @SerializedName("servicecompletiondate")
    private PojoServiceStatus servicecompletiondate;

    public PojoServiceStatus getServicecompletiondate() {
        return servicecompletiondate;
    }

    public void setServicecompletiondate(PojoServiceStatus servicecompletiondate) {
        this.servicecompletiondate = servicecompletiondate;
    }

    //getprepaidservicelist
    @SerializedName("prepaidServiceList")
    private ArrayList<PojoPrepaidServices> prepaidServiceList;

    public ArrayList<PojoPrepaidServices> getPrepaidServiceList() {
        return prepaidServiceList;
    }

    public void setPrepaidServiceList(ArrayList<PojoPrepaidServices> prepaidServiceList) {
        this.prepaidServiceList = prepaidServiceList;
    }

    //getaddonlist(postpaidlist)
    @SerializedName("postpaidServiceList")
    private ArrayList<PojoAddOnServices> postpaidServiceList;

    public ArrayList<PojoAddOnServices> getPostpaidServiceList() {
        return postpaidServiceList;
    }

    public void setPostpaidServiceList(ArrayList<PojoAddOnServices> postpaidServiceList) {
        this.postpaidServiceList = postpaidServiceList;
    }

    //
    @SerializedName("feedbackList")
    private ArrayList<PojoFeedbackList> feedbackList;

    public ArrayList<PojoFeedbackList> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(ArrayList<PojoFeedbackList> feedbackList) {
        this.feedbackList = feedbackList;
    }

    //getlostitems
    @SerializedName("lostItemList")
    private ArrayList<PojoLostItems> lostItemList;

    public ArrayList<PojoLostItems> getLostItemList() {
        return lostItemList;
    }

    public void setLostItemList(ArrayList<PojoLostItems> lostItemList) {
        this.lostItemList = lostItemList;
    }

    @SerializedName("policydetails")
    private ArrayList<PojoPolicyDetails> policydetails;

    public ArrayList<PojoPolicyDetails> getPolicydetails() {
        return policydetails;
    }

    public void setPolicydetails(ArrayList<PojoPolicyDetails> policydetails) {
        this.policydetails = policydetails;
    }
    //getweblinks
    @SerializedName("getweblinks")
    private PojoWebLinks getweblinks;

    public PojoWebLinks getGetweblinks() {
        return getweblinks;
    }

    public void setGetweblinks(PojoWebLinks getweblinks) {
        this.getweblinks = getweblinks;
    }


    //get invoice list
    @SerializedName("uploadedInvoice")
    private ArrayList<PojoInvoicesList> uploadedInvoice;
    @SerializedName("AdditionalServicesList")
    private ArrayList<PojoAddServiceList> AdditionalServicesList;

    public ArrayList<PojoInvoicesList> getUploadedInvoice() {
        return uploadedInvoice;
    }

    public void setUploadedInvoice(ArrayList<PojoInvoicesList> uploadedInvoice) {
        this.uploadedInvoice = uploadedInvoice;
    }

    public ArrayList<PojoAddServiceList> getAdditionalServicesList() {
        return AdditionalServicesList;
    }

    public void setAdditionalServicesList(ArrayList<PojoAddServiceList> additionalServicesList) {
        AdditionalServicesList = additionalServicesList;
    }

    //get claim list
    @SerializedName("ClaimList")
    private ArrayList<PojoAllClaimList> ClaimList;

    public ArrayList<PojoAllClaimList> getClaimList() {
        return ClaimList;
    }
    public void setClaimList(ArrayList<PojoAllClaimList> claimList) {
        ClaimList = claimList;
    }

    @SerializedName("claimType")
    private ArrayList<PojoClaimTypes> claimType;

    public ArrayList<PojoClaimTypes> getClaimType() {
        return claimType;
    }

    public void setClaimType(ArrayList<PojoClaimTypes> claimType) {
        this.claimType = claimType;
    }

    @SerializedName("claimSymptoms")
    private ArrayList<PojoSymptoms> claimSymptoms;

    public ArrayList<PojoSymptoms> getClaimSymptoms() {
        return claimSymptoms;
    }

    public void setClaimSymptoms(ArrayList<PojoSymptoms> claimSymptoms) {
        this.claimSymptoms = claimSymptoms;
    }
    @SerializedName("trackClaimStatus")
    private ArrayList<PojoTrackClaimList> trackClaimStatus;

    public ArrayList<PojoTrackClaimList> getTrackClaimStatus() {
        return trackClaimStatus;
    }

    public void setTrackClaimStatus(ArrayList<PojoTrackClaimList> trackClaimStatus) {
        this.trackClaimStatus = trackClaimStatus;
    }
}