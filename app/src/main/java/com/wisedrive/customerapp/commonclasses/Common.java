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
}


