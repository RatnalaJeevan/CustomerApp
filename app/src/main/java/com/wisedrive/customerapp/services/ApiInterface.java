package com.wisedrive.customerapp.services;


import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.ItemsList;
import com.wisedrive.customerapp.pojos.PojoAddService;
import com.wisedrive.customerapp.pojos.PojoAdditionalService;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoFeedbackList;
import com.wisedrive.customerapp.pojos.PojoInitiateClaim;
import com.wisedrive.customerapp.pojos.PojoLostItems;
import com.wisedrive.customerapp.pojos.PojoPeriodicMaintenanceServices;
import com.wisedrive.customerapp.pojos.PojoScheduleAdress;
import com.wisedrive.customerapp.pojos.PojoServices;
import com.wisedrive.customerapp.pojos.PojoSubmitItems;
import com.wisedrive.customerapp.pojos.PojoUploadInvoice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
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

    @GET("/AddressDetails/getpincode")
    Call<AppResponse> get_pincode_list(@Query("pincode") String pincode);

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
