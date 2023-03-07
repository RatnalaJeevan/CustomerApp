package com.wisedrive.customerapp.commonclasses;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Common {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public final static String TAG = "Log";
    public final static int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public final static int MY_PERMISSIONS_REQUEST_LOCATION = 100;

    public static void CallToast(Context context, String message, int duration) {
        Toast.makeText(context,message,duration).show();
    }

    public final static String PhoneNumberRegix = "[0-9]{10}";

    public static String getCurrentDateDay() {
        Date current = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String parsed = dateFormat.format(current);
        return parsed;
    }
    public static String getDateFromString(String dateStr) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date got = format.parse(dateStr);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            return format.format(got);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getdate(String dateStr) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date got = format.parse(dateStr);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            return format.format(got);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getDeviceIMEI(Context c) {
        String deviceUniqueIdentifier = null;
        try {
            TelephonyManager e = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                deviceUniqueIdentifier = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);

            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deviceUniqueIdentifier = e.getImei();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceUniqueIdentifier = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceUniqueIdentifier;
    }
}


