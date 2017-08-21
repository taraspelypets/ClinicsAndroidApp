package com.taraspelypets.clinics;

import android.app.Application;
import android.content.SharedPreferences;

import com.taraspelypets.cliniclib.ClinicLib;

/**
 * Created by Taras on 04.08.2017.
 */

public class App extends Application {

    public static String SHARED_PREFERENCES_SETTINGS = "SETTINGS";
    public static String SETTINGS_HOST_URL = "SETTINGS_HOST_URL";




    private String hostUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES_SETTINGS, 0);
        hostUrl = sharedPreferences.getString(SETTINGS_HOST_URL, getResources().getString(R.string.server_url));
        sharedPreferences.edit().putString(SETTINGS_HOST_URL, hostUrl).apply();
        ClinicLib.init(this, hostUrl);
    }


    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

}
