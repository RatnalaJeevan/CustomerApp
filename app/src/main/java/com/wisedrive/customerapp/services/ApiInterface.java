package com.wisedrive.customerapp.services;


import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddNewClaim;
import com.wisedrive.customerapp.pojos.PojoAddYourCar;
import com.wisedrive.customerapp.pojos.PojoAdditionalService;
import com.wisedrive.customerapp.pojos.PojoBookService;
import com.wisedrive.customerapp.pojos.PojoCreateLead;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoDeleteAdress;
import com.wisedrive.customerapp.pojos.PojoGetVehdetails;
import com.wisedrive.customerapp.pojos.PojoPayParttial;
import com.wisedrive.customerapp.pojos.PojoRequestVehInsp;
import com.wisedrive.customerapp.pojos.PojoScheduleAdress;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.PojoSubmitItems;
import com.wisedrive.customerapp.pojos.PojoSubmitSelfInsp;
import com.wisedrive.customerapp.pojos.PojoUpdateDocs;
import com.wisedrive.customerapp.pojos.PojoUpdateOdometer;
import com.wisedrive.customerapp.pojos.PojoUpgradePackage;

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

    @POST("/Profile/reqForEdit")
    Call<AppResponse> req_for_edit(@Body PojoCreateLead pojoCreateLead);

    @GET("/AddVehicle/getBrandList")
    Call<AppResponse> getBrandList();

    @GET("/AddVehicle/getModelList")
    Call<AppResponse> getModelList(@Query("brandId") String brandId);


    @GET("/Packages/getpackListNew")
    Call<AppResponse> getNewPackList(@Query("leadId") String leadId,@Query("customerId") String customerId);

    @GET("/Packages/getpackDetails")
    Call<AppResponse> getPackDetails(@Query("leadId") String leadId,@Query("customerId") String customerId,
                                     @Query("packageId") String packageId);

    @GET("/Packages/getProductList")
    Call<AppResponse> get_product_list(@Query("packageId") String packageId);

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
    Call<AppResponse> sell_package_to_customer(@Body PojoSellPackage pojoSellPackage);

    @POST("/Packages/sell/Package")
    Call<AppResponse> sell_package(@Body PojoSellPackage pojoSellPackage);

    @GET("/Packages/vehicleProductDetails")
    Call<AppResponse> getVehProductDetails(@Query("vehicleId") String vehicleId);


    @GET("/NewService/serviceList")
    Call<AppResponse> getServiceList(@Query("vehicleId") String vehicleId,@Query("productId") String productId,
                                     @Query("packId") String packId,@Query("packType") String packType,
                                     @Query("customerId") String customerId);

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


    @GET("/CustomerLogin/getStatus")
    Call<AppResponse> get_cust_status(@Query("customerId") String customerId,@Query("leadId") String leadId);


    @GET("/CustomerLogin/getotp")
    Call<AppResponse> getOTP(@Query("phoneNo") String phoneNo);

    @GET("/CustomerLogin/verifyotp")
    Call<AppResponse> verifyOtp(@Query("phoneNo") String phoneNo,@Query("otp") String otp);

    @GET("/CustomerActivationCode/requestActivationCode")
    Call<AppResponse> request_activation_code(@Query("phoneNo") String phoneNo);

    @GET("/CustomerActivationCode/verifyActivationcode")
    Call<AppResponse> verify_activation_code(@Query("phoneNo") String phoneNo,@Query("code") String code);


    @GET("/AddressDetails/checkaddress")
    Call<AppResponse> check_address(@Query("pincode") String pincode,@Query("vehicleId") String vehicleId);

    @GET("/Package/getsupportdetails")
    Call<AppResponse> get_support_details();



    @GET("/LostItem/itemlist")
    Call<AppResponse> get_lostitems();

    @POST("/LostItem/updatelostitems")
    Call<AppResponse> update_lostitems(@Body PojoSubmitItems post_service);


    @GET("/Package/getpolicydetails")
    Call<AppResponse> get_policydetails();

    @GET("/Package/getweblinks")
    Call<AppResponse> get_weblinks();


    @POST("/Package/getAddservicedetails")
    Call<AppResponse> getAddServiceDetails(@Body PojoAdditionalService pojoaddServices);



    @POST("/AddVehicle/updateOdometer")
    Call<AppResponse> update_odo(@Body PojoUpdateOdometer pojoUpdateOdometer);


    @GET("/AddVehicle/getOdometerHistory")
    Call<AppResponse> get_odo_history(@Query("vehId") String vehId,@Query("leadVehId") String leadVehId);

    @GET("/Profile/documentList")
    Call<AppResponse> get_doc_list(@Query("vehicleId") String vehicleId,@Query("customerId") String customerId,
                                   @Query("leadVehId") String leadVehId,@Query("leadId") String leadId);

    @POST("/Profile/updateDocument")
    Call<AppResponse> update_doc(@Body PojoUpdateDocs pojoUpdateDocs);

    @GET("/Profile/docvehicleList")
    Call<AppResponse> get_doc_veh_list(@Query("customerId") String customerId);


    @GET("/Packages/getPackageBasedOnPlan")
    Call<AppResponse> get_pack_based_plan(@Query("leadId") String leadId,
                                          @Query("customerId") String customerId,
                                          @Query("planId") String planId);


    @GET("/Packages/getUpgradePackList")
    Call<AppResponse> get_upgrade_pac_list(@Query("leadId") String leadId, @Query("customerId") String customerId,
                                          @Query("vehicleId") String vehicleId,@Query("packageId") String packageId,
                                           @Query("categoryId") String categoryId,@Query("iswithActivePack") String iswithActivePack);
    @GET("/Packages/getPartialPaymentProducts")
    Call<AppResponse> get_partial_payment_products(@Query("packageId") String packageId);

    @POST("/Inspection/getDetails")
    Call<AppResponse> getVeh_details(@Body PojoGetVehdetails pojoGetVehdetails);

    @POST("/AddVehicle/addVehicle")
    Call<AppResponse> add_car(@Body PojoAddYourCar pojoAddYourCar);

    @GET("/Inspection/getPartDetails")
    Call<AppResponse> get_part_details(@Query("vehicleId") String vehicleId, @Query("leadVehId") String leadVehId,
                                           @Query("partId") String partId,@Query("winsvehId") String winsvehId);

    @GET("/Packages/getWarrantyBenifits")
    Call<AppResponse> get_warranty_benefits();


    @POST("/Inspection/submitInspectionDetails")
    Call<AppResponse> self_insp(@Body PojoSubmitSelfInsp pojoSubmitSelfInsp);

    @POST("/Packages/completePartialPayment")
    Call<AppResponse> complete_partial(@Body PojoPayParttial pojoPayParttial);

    @GET("/Packages/getServiceDetails")
    Call<AppResponse> get_service_details(@Query("servicepackId") String servicepackId, @Query("count") String count,
                                          @Query("dppId") String dppId);

    @GET("/AddressDetails/addressList")
    Call<AppResponse> get_address_list(@Query("customerId") String customerId);

    @POST("/AddressDetails/deleteAddress")
    Call<AppResponse> delete_adress(@Body PojoDeleteAdress pojoDeleteAdress);

    @GET("/Packages/displayPopup")
    Call<AppResponse> get_display_popup(@Query("customerId") String customerId,
                                       @Query("leadId") String leadId );



}
