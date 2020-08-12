package com.livetv.android.utils;

import android.app.UiModeManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.livetv.android.LiveTvApplication;
import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class Device {
    public static boolean isHDMIStatusSet = false;
    public static boolean treatAsBox = false;

    public static boolean canTreatAsBox() {
        return treatAsBox;
    }

    public static void setHDMIStatus() {
        boolean z;
        File switchFile = new File("/sys/devices/virtual/switch/hdmi/state");
        if (!switchFile.exists()) {
            switchFile = new File("/sys/class/switch/hdmi/state");
        }
        try {
            Scanner switchFileScanner = new Scanner(switchFile);
            int switchValue = switchFileScanner.nextInt();
            switchFileScanner.close();
            if (switchValue > 0) {
                z = true;
            } else {
                z = false;
            }
            treatAsBox = z;
        } catch (Exception e) {
            treatAsBox = false;
        }
        if (!treatAsBox && LiveTvApplication.getAppContext().getPackageManager().hasSystemFeature("android.software.live_tv")) {
            treatAsBox = true;
        }
        if (((UiModeManager) LiveTvApplication.getAppContext().getSystemService("uimode")).getCurrentModeType() == 4 && !treatAsBox) {
            treatAsBox = true;
        }
        if (!LiveTvApplication.getAppContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen") && !treatAsBox) {
            treatAsBox = true;
        }
        isHDMIStatusSet = true;
    }

    public static String getFW() {
        return VERSION.RELEASE;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getCountry() {
        String country = "";
        try {
            TelephonyManager tm = (TelephonyManager) LiveTvApplication.getAppContext().getSystemService("phone");
            String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                country = simCountry.toLowerCase(Locale.US);
            }
            if (!TextUtils.isEmpty(country)) {
                return country;
            }
            if (tm.getPhoneType() != 2) {
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    country = networkCountry.toLowerCase(Locale.US);
                }
            }
            if (!TextUtils.isEmpty(country)) {
                return country;
            }
            if (VERSION.SDK_INT >= 24) {
                return LiveTvApplication.getAppContext().getResources().getConfiguration().getLocales().get(0).getCountry();
            }
            return LiveTvApplication.getAppContext().getResources().getConfiguration().locale.getCountry();
        } catch (Exception e) {
            return "mx";
        }
    }

    public static String getIdentifier() {
        String id = DataManager.getInstance().getString("deviceIdentifier", "");
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        String id2 = UUID.randomUUID().toString();
        DataManager.getInstance().saveData("deviceIdentifier", id2);
        return id2;
    }

    public static String getVersion() {
        String versionName = "1.0";
        PackageManager packageManager = LiveTvApplication.getAppContext().getPackageManager();
        if (packageManager == null) {
            return versionName;
        }
        try {
            return packageManager.getPackageInfo(LiveTvApplication.getAppContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "1.0";
        }
    }

    public static String getVersionInstalled() {
        try {
            return LiveTvApplication.getAppContext().getPackageManager().getPackageInfo(LiveTvApplication.getAppContext().getPackageName(), 128).versionName;
        } catch (Exception e) {
            return "0.0.1";
        }
    }
}
