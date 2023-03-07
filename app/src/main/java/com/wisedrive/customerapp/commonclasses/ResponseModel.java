package com.wisedrive.customerapp.commonclasses;

import com.google.gson.annotations.SerializedName;
import com.wisedrive.customerapp.pojos.CustomerLoginDetails;
import com.wisedrive.customerapp.pojos.CustomerVehicleDetails;
import com.wisedrive.customerapp.pojos.PojoAddOnServices;
import com.wisedrive.customerapp.pojos.PojoAddServiceList;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoAllClaimList;
import com.wisedrive.customerapp.pojos.PojoAppUpdateDetails;
import com.wisedrive.customerapp.pojos.PojoBankList;
import com.wisedrive.customerapp.pojos.PojoCFOrderData;
import com.wisedrive.customerapp.pojos.PojoClaimTypes;
import com.wisedrive.customerapp.pojos.PojoCouponList;
import com.wisedrive.customerapp.pojos.PojoFeedbackList;
import com.wisedrive.customerapp.pojos.PojoHappyCode;
import com.wisedrive.customerapp.pojos.PojoInvoicesList;
import com.wisedrive.customerapp.pojos.PojoLostItems;
import com.wisedrive.customerapp.pojos.PojoMyPayments;
import com.wisedrive.customerapp.pojos.PojoNearestServiceCentreDetails;
import com.wisedrive.customerapp.pojos.PojoNewClaimList;
import com.wisedrive.customerapp.pojos.PojoPackageList;
import com.wisedrive.customerapp.pojos.PojoPolicyDetails;
import com.wisedrive.customerapp.pojos.PojoPrepaidServices;
import com.wisedrive.customerapp.pojos.PojoServiceStatus;
import com.wisedrive.customerapp.pojos.PojoServiceflow;
import com.wisedrive.customerapp.pojos.PojoServices;
import com.wisedrive.customerapp.pojos.PojoSupportDetails;
import com.wisedrive.customerapp.pojos.PojoSymptoms;
import com.wisedrive.customerapp.pojos.PojoTrackClaimList;
import com.wisedrive.customerapp.pojos.PojoTrackClaims;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.pojos.PojoVehObj;
import com.wisedrive.customerapp.pojos.PojoWarrantyDetails;
import com.wisedrive.customerapp.pojos.PojoWebLinks;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Claim_Type_New_Cus_App;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Extended_Warranty_Plan;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Select_Model;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;
import com.wisedrive.customerapp.pojos.Pojo_Service_and_Maintanance_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;
import com.wisedrive.customerapp.pojos.Pojo_Upgrade_Save;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;
import com.wisedrive.customerapp.pojos.Pojo_initiate_Claims_Photos;

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

    @SerializedName("coupon_id")
    private String coupon_id;
    @SerializedName("coupon_type")
    private String coupon_type;
    @SerializedName("coupon_code")
    private String coupon_code;
    @SerializedName("discount_amount")
    private double discount_amount;

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    //    "error_msg":"","expiration_date":"2023-07-01","description":"Apply this coupon and get 50 off","coupon_id":1,
//            "coupon_code":"WD4c51eb74","is_valid":"Y","discount_amount":50.0,"coupon_type":1
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
//    @SerializedName("vehicleList")
//    private ArrayList<PojoVehDetails> vehicleList;
//
//    public ArrayList<PojoVehDetails> getVehicleList() {
//        return vehicleList;
//    }
//
//    public void setVehicleList(ArrayList<PojoVehDetails> vehicleList) {
//        this.vehicleList = vehicleList;
//    }

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
//    @SerializedName("ClaimList")
//    private ArrayList<PojoAllClaimList> ClaimList;
//
//    public ArrayList<PojoAllClaimList> getClaimList() {
//        return ClaimList;
//    }
//    public void setClaimList(ArrayList<PojoAllClaimList> claimList) {
//        ClaimList = claimList;
//    }

//    @SerializedName("claimType")
//    private ArrayList<PojoClaimTypes> claimType;
//
//    public ArrayList<PojoClaimTypes> getClaimType() {
//        return claimType;
//    }



    @SerializedName("claimSymptoms")
    private ArrayList<PojoSymptoms> claimSymptoms;

    public ArrayList<PojoSymptoms> getClaimSymptoms() {
        return claimSymptoms;
    }

    public void setClaimSymptoms(ArrayList<PojoSymptoms> claimSymptoms) {
        this.claimSymptoms = claimSymptoms;
    }
//    @SerializedName("trackClaimStatus")
//    private ArrayList<PojoTrackClaimList> trackClaimStatus;
//
//    public ArrayList<PojoTrackClaimList> getTrackClaimStatus() {
//        return trackClaimStatus;
//    }


    @SerializedName("brandList")
    private ArrayList<Pojo_Select_Make_list> brandList;

    public ArrayList<Pojo_Select_Make_list> getBrandList() {
        return brandList;
    }

    @SerializedName("modelList")
    private ArrayList<Pojo_Select_Model> modelList;

    public ArrayList<Pojo_Select_Model> getModelList() {
        return modelList;
    }

    @SerializedName("providerList")
    ArrayList<PojoBankList> providerList;

    public ArrayList<PojoBankList> getProviderList() {
        return providerList;
    }

    @SerializedName("getvehicleimages")
    private ArrayList<Pojo_Upload_Image> getvehicleimages;

    public ArrayList<Pojo_Upload_Image> getGetvehicleimages() {
        return getvehicleimages;
    }

    @SerializedName("WarrantyPackList")
    private ArrayList<Pojo_Extended_Warranty_Plan> WarrantyPackList;
    @SerializedName("ServicePackList")
    private ArrayList<Pojo_Service_and_Maintanance_Plans> ServicePackList;
    @SerializedName("ComboPackList")
    private ArrayList<Pojo_Combo_Plans> ComboPackList;

    public ArrayList<Pojo_Extended_Warranty_Plan> getWarrantyPackList() {
        return WarrantyPackList;
    }

    public ArrayList<Pojo_Service_and_Maintanance_Plans> getServicePackList() {
        return ServicePackList;
    }

    public ArrayList<Pojo_Combo_Plans> getComboPackList() {
        return ComboPackList;
    }

    @SerializedName("myCars")
    private ArrayList<Pojo_Class_Mycar> myCars;

    @SerializedName("vehicleList")
    private ArrayList<Pojo_Class_Mycar> vehicleList;

    public ArrayList<Pojo_Class_Mycar> getMyCars() {
        return myCars;
    }

    public ArrayList<Pojo_Class_Mycar> getVehicleList() {
        return vehicleList;
    }

    @SerializedName("MyCarCount")
    private Pojo_Class_Mycar MyCarCount;

    public Pojo_Class_Mycar getMyCarCount() {
        return MyCarCount;
    }
    @SerializedName("PaymentCount")
    private Pojo_Class_Mycar PaymentCount;

    public Pojo_Class_Mycar getPaymentCount() {
        return PaymentCount;
    }

    @SerializedName("productList")
    private ArrayList<Pojo_Additional_Services> productList;

    public ArrayList<Pojo_Additional_Services> getProductList() {
        return productList;
    }

    @SerializedName("ServiceDetails")
    private ArrayList<Pojo_Service_list> ServiceDetails;

    public ArrayList<Pojo_Service_list> getServiceDetails() {
        return ServiceDetails;
    }

    @SerializedName("Description")
    private Pojo_Service_list Description;

    @SerializedName("PriceDetails")
    private Pojo_Service_list PriceDetails;
    @SerializedName("packExpiry")
    private Pojo_Service_list packExpiry;

    public Pojo_Service_list getPackExpiry() {
        return packExpiry;
    }

    public Pojo_Service_list getDescription() {
        return Description;
    }

    public Pojo_Service_list getPriceDetails() {
        return PriceDetails;
    }


     @SerializedName("AddonList")
    private ArrayList<Pojo_Class_Addons_List> AddonList;

    public ArrayList<Pojo_Class_Addons_List> getAddonList() {
        return AddonList;
    }

    @SerializedName("VehicleProductDetails")
    private ArrayList<Pojo_My_Car_page_package_list> VehicleProductDetails;

    public ArrayList<Pojo_My_Car_page_package_list> getVehicleProductDetails() {
        return VehicleProductDetails;
    }

    @SerializedName("ConfirmationList")
    private ArrayList<Pojo_My_Car_page_package_list> ConfirmationList;

    public ArrayList<Pojo_My_Car_page_package_list> getConfirmationList() {
        return ConfirmationList;
    }

    @SerializedName("CashfreeorderData")
    private PojoCFOrderData CashfreeorderData;

    public PojoCFOrderData getCashfreeorderData() {
        return CashfreeorderData;
    }

    @SerializedName("VehicleServiceList")
    private ArrayList<Pojo_Showroom_services> VehicleServiceList;

    public ArrayList<Pojo_Showroom_services> getVehicleServiceList() {
        return VehicleServiceList;
    }

    @SerializedName("TrackService")
    private ArrayList<Pojo_Tracking_page> TrackService;

    public ArrayList<Pojo_Tracking_page> getTrackService() {
        return TrackService;
    }

    @SerializedName("DateList")
    private ArrayList<Pojo_Select_Date> DateList;

    public ArrayList<Pojo_Select_Date> getDateList() {
        return DateList;
    }

    @SerializedName("vehicleObj")
    private PojoVehObj vehicleObj;

    public PojoVehObj getVehicleObj() {
        return vehicleObj;
    }

    @SerializedName("claimVehicleList")
    private ArrayList<Pojo_Select_Your_Vehicle_no> claimVehicleList;

    public ArrayList<Pojo_Select_Your_Vehicle_no> getClaimVehicleList() {
        return claimVehicleList;
    }

    @SerializedName("claimType")
    private ArrayList<Pojo_Claim_Type_New_Cus_App> claimType;

    public ArrayList<Pojo_Claim_Type_New_Cus_App> getClaimType() {
        return claimType;
    }

    @SerializedName("ClaimImages")
    private ArrayList<Pojo_initiate_Claims_Photos> ClaimImages;

    public ArrayList<Pojo_initiate_Claims_Photos> getClaimImages() {
        return ClaimImages;
    }

    @SerializedName("QuestionList")
    private ArrayList<Pojo_Q_And_A> QuestionList;

    public ArrayList<Pojo_Q_And_A> getQuestionList() {
        return QuestionList;
    }

    @SerializedName("claimList")
    private ArrayList<PojoNewClaimList> claimList;

    public ArrayList<PojoNewClaimList> getClaimList() {
        return claimList;
    }

    @SerializedName("trackClaimStatus")
    private ArrayList<PojoTrackClaims> trackClaimStatus;

    public ArrayList<PojoTrackClaims> getTrackClaimStatus() {
        return trackClaimStatus;
    }


    @SerializedName("upgradeproductList")
    private ArrayList<Pojo_Upgrade_Save> upgradeproductList;

    @SerializedName("upgradeservicesList")
    private ArrayList<Pojo_Upgrade_Save> upgradeservicesList;

    public ArrayList<Pojo_Upgrade_Save> getUpgradeproductList() {
        return upgradeproductList;
    }

    public ArrayList<Pojo_Upgrade_Save> getUpgradeservicesList() {
        return upgradeservicesList;
    }

    @SerializedName("upgradedescription")
    private Pojo_Upgrade_Save upgradedescription;

    public Pojo_Upgrade_Save getUpgradedescription() {
        return upgradedescription;
    }

    @SerializedName("CouponCodeList")
    private ArrayList<PojoCouponList> CouponCodeList;

    public ArrayList<PojoCouponList> getCouponCodeList() {
        return CouponCodeList;
    }

    @SerializedName("paymentHistory")
    private ArrayList<PojoMyPayments> paymentHistory;

    public ArrayList<PojoMyPayments> getPaymentHistory() {
        return paymentHistory;
    }
}