package com.wisedrive.customerapp.commonclasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.wisedrive.customerapp.adapters.AdapterCustomerVehicleList;
import com.wisedrive.customerapp.pojos.PojoVehDetails;

import java.util.ArrayList;

public class SPHelper {
    public static SharedPreferences prefs;
    public static String customer_phoneno="customer_phoneno";
    public static String package_activated="package_activated";
    public static String otp_activated="otp_activated";
    public static String customer_id="customer_id";
    public static String customer_veh_id="";
    public static String customer_exp_kms="";
    public static String veh_valid_from="";
    public static String veh_valid_to="";
    public static String customer_veh_no="";
    public static String veh_id="";
    public static String customer_name="";
    public static String veh_model="";
    public static String veh_make="";
    public static String package_id="";
    public static String status_id="";
    public static String customer_selected_pincod="";
    public static String service_centre_id="";
    public static String customer_selected_address_id="";
    public static String is_serving="";
    public static String get_customer_address="";
    public static String service_id="";
    public static String package_name="";
    public static String blogurl="";
    public static String privacypolicy="";
    public static String tnc="";
    public static String copyrights="";
    public static String comingfrom="";
    public static String customer_support_phoneno="customer_support_phoneno";
    public static String customer_support_email="customer_support_email";
    public static String viewpolicy="";
    public static String can_skip="";
    public static String app_url="";
    public static String from="";
    public static String by="";
    public static String awskey="awskey";
    public static String awssecret="awssecret";
    public static String comet_authkey="comet_authkey";
    public static String comet_region="comet_region";
    public static String comet_appid="comet_appid";
    public static ArrayList<PojoVehDetails> vehDetailsList=new ArrayList<>();
    public static String claim_type_id="";
    public static String selected_symptom="";
    public static String claim_id="";
    public static String veh_name="";
    public static String claim_type="";
    public static String claim_code="";

    private  static String spName="CustomerApp";

    public static void sharedPreferenceInitialization(Context ctx) {
        prefs = ctx.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }

    public static void saveSPdata(Context ctx, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSPData(Context ctx, String key, String defaultValue) {
        prefs = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }
}
