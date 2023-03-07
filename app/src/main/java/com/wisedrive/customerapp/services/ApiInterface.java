package com.wisedrive.customerapp.services;


import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddNewClaim;
import com.wisedrive.customerapp.pojos.PojoAddService;
import com.wisedrive.customerapp.pojos.PojoAdditionalService;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoBookService;
import com.wisedrive.customerapp.pojos.PojoCreateLead;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoFeedbackList;
import com.wisedrive.customerapp.pojos.PojoInitiateClaim;
import com.wisedrive.customerapp.pojos.PojoPeriodicMaintenanceServices;
import com.wisedrive.customerapp.pojos.PojoRequestVehInsp;
import com.wisedrive.customerapp.pojos.PojoScheduleAdress;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.PojoServices;
import com.wisedrive.customerapp.pojos.PojoSubmitItems;
import com.wisedrive.customerapp.pojos.PojoUpgradePackage;
import com.wisedrive.customerapp.pojos.PojoUploadInvoice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{

    @GET("/CustomerLogin/insertotp")
    Call<AppResponse> insertOtp(@Query("phoneNo") String phoneNo,@Query("is_login") String is_login);

    @GET("/CustomerLogin/validateotp")
    Call<AppResponse> validateOtp(@Query("phoneNo") String phoneNo,@Query("otp") String otp);

    @POST("/CustomerLogin/createLead")
    Call<AppResponse> createLead(@Body PojoCreateLead pojoCreateLead);

    @GET("/AddVehicle/getBrandList")
    Call<AppResponse> getBrandList();

    @GET("/AddVehicle/getModelList")
    Call<AppResponse> getModelList(@Query("brandId") String brandId);

    @GET("/AddVehicle/getProviderList")
    Call<AppResponse> getins_pro_list();

    @GET("/AddVehicle/getimagelist")
    Call<AppResponse> get_car_imagelist();

    @GET("/Packages/getpackList")
    Call<AppResponse> getPackList(@Query("leadId") String leadId,@Query("customerId") String customerId);

    @GET("/Packages/getpackDetails")
    Call<AppResponse> getPackDetails(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                     @Query("packageId") String packageId);

    @GET("/Packages/getpackDescription")
    Call<AppResponse> getPackDescription(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                     @Query("packageId") String packageId,@Query("productId") String productId,
                                     @Query("mainPackId") String mainPackId,@Query("categoryId") String categoryId,
                                         @Query("vehicleId") String vehicleId);

    @GET("/Packages/getAddonList")
    Call<AppResponse> getAddonList(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                   @Query("packId") String packId,@Query("categoryId") String categoryId,
                                   @Query("mainPackId") String mainPackId);

    @GET("/Packages/getCouponCodeList")
    Call<AppResponse> get_coupon_list(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                   @Query("packId") String packId,@Query("categoryId") String categoryId,
                                   @Query("mainPackId") String mainPackId,@Query("amount") double amount,
                                      @Query("isUpgrade") String isUpgrade);

    @GET("/Packages/getCouponDetails")
    Call<AppResponse> get_coupon_details(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                      @Query("packId") String packId,@Query("categoryId") String categoryId,
                                      @Query("mainPackId") String mainPackId,@Query("amount") double amount,
                                         @Query("couponCode") String couponCode);

    @GET("/AddVehicle/vehicleList")
    Call<AppResponse> getVehList(@Query("leadId") String leadId,@Query("customerId") String customerId);

    @GET("/AddVehicle/vehicleCount")
    Call<AppResponse> getVehCount(@Query("leadId") String leadId,@Query("customerId") String customerId);

    @POST("/cashfree/generateOrder")
    Call<AppResponse> generate_order(@Body PojoCreateOrder pojoCreateOrder);

    @POST("/Packages/sell/Package/to/customer")
    Call<AppResponse> sell_package(@Body PojoSellPackage pojoSellPackage);


    @GET("/Packages/vehicleProductDetails")
    Call<AppResponse> getVehProductDetails(@Query("vehicleId") String vehicleId);


    @GET("/NewService/serviceList")
    Call<AppResponse> getServiceList(@Query("vehicleId") String vehicleId,@Query("productId") String productId,
                                     @Query("packId") String packId,@Query("packType") String packType);

    @POST("/NewService/bookService")
    Call<AppResponse> book_service(@Body PojoBookService pojoBookService);

    @GET("/NewService/trackService")
    Call<AppResponse> trackService(@Query("serviceId") String serviceId);

    @GET("/NewService/dateList")
    Call<AppResponse> getDateList(@Query("productId") String productId);

    @GET("/AddressDetails/getpincode")
    Call<AppResponse> get_pincode_list(@Query("pincode") String pincode);

    @POST("/Packages/requestinspection")
    Call<AppResponse> req_insp(@Body PojoRequestVehInsp pojoRequestVehInsp);

    @GET("/NewClaims/getClaimType")
    Call<AppResponse> get_new_claim_type(@Query("vehicleId") String vehicleId);

    @GET("/NewClaims/NewclaimList")
    Call<AppResponse> getNewClaimList(@Query("vehicleId") String vehicleId);

    @GET("/NewClaims/claimVehicleList")
    Call<AppResponse> getNewClaimVehList(@Query("customerId") String customerId);

    @GET("/NewClaims/getSymptoms")
    Call<AppResponse> getNewSymptoms(@Query("typeId") String typeId);

    @GET("/NewClaims/getClaimImages")
    Call<AppResponse> get_claim_images();

    @GET("/NewClaims/trackClaims")
    Call<AppResponse> track_claims(@Query("claimId") String claimId);

    @POST("/NewClaims/newaddClaim")
    Call<AppResponse> add_new_claim(@Body PojoAddNewClaim pojoAddNewClaim);

    @GET("/AcivateAcccount/activate")
    Call<AppResponse> get_act_code(@Query("activationCode") String activationCode,
                                   @Query("customerId") String customerId);

    @GET("/Packages/paymentConfirmation")
    Call<AppResponse> get_payment_confirmation_page(@Query("dppId") String dppId);

    @GET("/AcivateAcccount/getpackDetails")
    Call<AppResponse> get_pack_details(@Query("activationCode") String activationCode);

    @POST("/AcivateAcccount/upgrade/Package/to/customer")
    Call<AppResponse> upgrade_package(@Body PojoUpgradePackage pojoUpgradePackage);


    @GET("/NewService/getweblinks")
    Call<AppResponse> get_new_web_links();


    @GET("/NewService/getsupportdetails")
    Call<AppResponse> get_help_support();

    @GET("/Packages/getPaymentHistory")
    Call<AppResponse> get_payment_history(@Query("pageNo") String pageNo,@Query("customerId") String customerId,
                                          @Query("leadId") String leadId);




    @GET("/CustomerLogin/getAppUpdateDetails")
    Call<AppResponse> getapp_update_deatails(@Query("appId") String appId,@Query("osType") String osType);

    @GET("/CustomerLogin/getotp")
    Call<AppResponse> getOTP(@Query("phoneNo") String phoneNo);

    @GET("/CustomerLogin/verifyotp")
    Call<AppResponse> verifyOtp(@Query("phoneNo") String phoneNo,@Query("otp") String otp);

    @GET("/CustomerActivationCode/requestActivationCode")
    Call<AppResponse> request_activation_code(@Query("phoneNo") String phoneNo);

    @GET("/CustomerActivationCode/verifyActivationcode")
    Call<AppResponse> verify_activation_code(@Query("phoneNo") String phoneNo,@Query("code") String code);

    @GET("/Homepage/vehicleList")
    Call<AppResponse> get_veh_details(@Query("customerId") String customerId);

    @POST("/Homepage/getVehicleDetails")
    Call<AppResponse> post_veh_details(@Body PojoPeriodicMaintenanceServices post);

    @GET("/AddressDetails/addressList")
    Call<AppResponse> get_address_list(@Query("customerId") String customerId);

    @POST("/AddressDetails/updateaddress")
    Call<AppResponse> post_address(@Body PojoAddresses post_address);


    @GET("/ServiceCentre/getNearestServiceCentre")
    Call<AppResponse> get_nearest_service_centre(@Query("vehicleId") String vehicleId,@Query("pincode") String pincode);

    @GET("/AddressDetails/checkaddress")
    Call<AppResponse> check_address(@Query("pincode") String pincode,@Query("vehicleId") String vehicleId);

    @GET("/Package/getsupportdetails")
    Call<AppResponse> get_support_details();

    @POST("/Service/bookService")
    Call<AppResponse> post_schedule_adress(@Body PojoScheduleAdress post_schedule);

    @POST("/Service/bookAddService")
    Call<AppResponse> post_schedule_add_service(@Body PojoAddService pojoAddService);

    @POST("/Package/getservicedetails")
    Call<AppResponse> get_service_details(@Body PojoServices post_service);

    @GET("/Package/getservicecompletiondate")
    Call<AppResponse> get_servicecompletion_date(@Query("serviceId") String serviceId);

    @GET("/VehiclePackageDetails/prepaidServiceList")
    Call<AppResponse> get_prepaid_servicelist(@Query("serviceId") String serviceId);

    @GET("/VehiclePackageDetails/postpaidSercviceList")
    Call<AppResponse> get_addon_servicelist(@Query("serviceId") String serviceId);

    @GET("/FeedbackItems/itemlist")
    Call<AppResponse> get_feedbackitems();

    @GET("/LostItem/itemlist")
    Call<AppResponse> get_lostitems();

    @POST("/LostItem/updatelostitems")
    Call<AppResponse> update_lostitems(@Body PojoSubmitItems post_service);

    @POST("/FeedbackItems/updatefeedback")
    Call<AppResponse> update_feedback(@Body PojoFeedbackList post_service);

    @GET("/Package/getpolicydetails")
    Call<AppResponse> get_policydetails();

    @GET("/Package/getweblinks")
    Call<AppResponse> get_weblinks();


    @GET("/Package/getUploadedInvoice")
    Call<AppResponse> get_uploadedinvoiceList(@Query("vehicleId") String vehicleId,@Query("customerId") String customerId);

    @GET("/Package/getAdditionalServicesList")
    Call<AppResponse> get_AdditionalServiceList(@Query("vehicleId") String vehicleId,@Query("customerId") String customerId,
                                                @Query("pageNo") String pageNo);

    @POST("/Package/uploadInvoice")
    Call<AppResponse> upload_invoice(@Body PojoUploadInvoice pojoUploadInvoice);

    @POST("/Package/getAddservicedetails")
    Call<AppResponse> getAddServiceDetails(@Body PojoAdditionalService pojoaddServices);

    @GET("Claims/ClaimList")
    Call<AppResponse> getClaimList(@Query("customerId") String customerId,@Query("fromDate") String fromDate,
                                   @Query("toDate") String toDate,@Query("text") String text,
                                   @Query("month") String month,@Query("year") String year,@Query("search") String search);

    @GET("/Claims/getClaimType")
    Call<AppResponse> getClaimType();

    @GET("/Claims/getClaimSymptoms")
    Call<AppResponse> getClaimSymptoms(@Query("claimTypeId") String claimTypeId);

    @POST("/Claims/addClaim")
    Call<AppResponse> post_add_claim(@Body PojoInitiateClaim pojoInitiateClaim);

    @GET("/Claims/trackClaims")
    Call<AppResponse> getTrackClaim(@Query("claimId") String claimId);

}
