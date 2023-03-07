package com.wisedrive.customerapp.commonclasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.wisedrive.customerapp.adapters.AdapterCustomerVehicleList;
import com.wisedrive.customerapp.adapters.Adapter_Yes_No;
import com.wisedrive.customerapp.pojos.PojoAnswerDetails;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class SPHelper {
    public static SharedPreferences prefs;
    public static String customer_phoneno="customer_phoneno";
    public static String package_activated="package_activated";
    public static String otp_activated="otp_activated";
    public static String customer_id="customer_id";
    public static String cust_name="cust_name";
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
    public static String lead_id="lead_id";
    public static String is_pack_activated="is_pack_activated";
    public static String carbrandid="";
    public static String carmodelid="";
    public static String imagestaken="imagestaken";
    public static String product_id="";
    public static String main_pack_id="";
    public static String cat_id="";
    public static double pack_amount;
    public static String selected_addon_id="";
    public static double final_amount;
    public static String cust_mail="cust_mail";
    public static String isSuccess="";
    public static String lead_veh_id="";
    public static String veh_no="";

    public static ArrayList<PojoAnswerDetails> answer_details=new ArrayList<>();
    public static ArrayList<Pojo_Q_And_A> quest_list=new ArrayList<>();

    public  static ArrayList<Pojo_Class_Addons_List> addon_list=new ArrayList<>();
    public static String cf_msg="";
    public static String car_model_name="";
    public static String dpp_id="";
    public static double upgrade_amount;
    public static String bbg_policy="";
    public static String warr_policy="";
    public static String brand_name="";
    public static String fragment_is="";
    public static String coupon_code="";
    public static String coupon_type="";
    public static double disc_amount;
    public static String coupon_id="";
    public static String actcode="";
    public static String product_name="";
    public static ArrayList<Pojo_Q_And_A> qa_list=new ArrayList<>();
    public static ArrayList<Pojo_yes_no> answerlist=new ArrayList<>();
    public static String selcted_veh_id="";
    public static String pack_type="";
    public static String msg="";
    public static String is_active="";
    public static String is_exp="";

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
