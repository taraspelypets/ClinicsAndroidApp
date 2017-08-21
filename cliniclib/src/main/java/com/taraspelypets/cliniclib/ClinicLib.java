package com.taraspelypets.cliniclib;

import android.content.Context;
import android.content.SharedPreferences;

import com.taraspelypets.cliniclib.service.AuthenticationService;
import com.taraspelypets.cliniclib.service.MapService;
import com.taraspelypets.cliniclib.service.SearchService;
import com.taraspelypets.cliniclib.service.UserAppointmentsService;
import com.taraspelypets.cliniclib.service.impl.AuthenticationServiceImpl;
import com.taraspelypets.cliniclib.service.impl.MapServiceImpl;
import com.taraspelypets.cliniclib.service.impl.SearchServiceImpl;
import com.taraspelypets.cliniclib.service.impl.UserAppointmentsServiceImpl;

/**
 * Created by Taras on 06.08.2017.
 */

public class ClinicLib {

    private final String CURRENT_USER_DATA = "CURRENT_USER_DATA";

    private final String USER_USERNAME = "USER_USERNAME";

    private final String USER_FIRST_NAME = "USER_FIRST_NAME";
    private final String USER_LAST_NAME = "USER_LAST_NAME";
    private final String USER_MIDDLE_NAME = "USER_MIDDLE_NAME";

    private final String USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";
    private final String USER_AUTHORITY_USER = "USER_AUTHORITY_USER";
    private final String USER_AUTHORITY_DOCTOR = "USER_AUTHORITY_DOCTOR";

    private DataObject.CurrentUser currentUser;
    private ResultListener gloalListener;

    private SharedPreferences sharedPreferences;

    private static volatile ClinicLib instance;
    String hostUrl;

    public static void init(Context context, String hostUrl) {
        ClinicLib localInstance = instance;
        if (localInstance == null) {
            synchronized (ClinicLib.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ClinicLib(context, hostUrl);
                }
            }
        }
    }

    public static ClinicLib getInstance(){
        if(instance != null){
            return instance;
        } else {
            throw new NullPointerException("You must initiate first. Call ClinicLib.init(Context context)");
        }
    }

    ClinicLib(Context context, String hostUrl){
        this.hostUrl = hostUrl;
        initServices(hostUrl);

        currentUser = new DataObject.CurrentUser();
        sharedPreferences = context.getSharedPreferences(CURRENT_USER_DATA, 0);
        currentUser.token = sharedPreferences.getString(USER_ACCESS_TOKEN, null);
        if (currentUser.token == null){
            return;
        }
        currentUser.username = sharedPreferences.getString(USER_USERNAME, "");
        currentUser.firstName = sharedPreferences.getString(USER_FIRST_NAME, "");
        currentUser.lastName = sharedPreferences.getString(USER_LAST_NAME, "");
        currentUser.middleName = sharedPreferences.getString(USER_MIDDLE_NAME, "");
        currentUser.userAuthority = sharedPreferences.getBoolean(USER_AUTHORITY_USER, false);
        currentUser.doctorAuthority = sharedPreferences.getBoolean(USER_AUTHORITY_DOCTOR, false);
    }

    public void setGlobalListener(ResultListener listener){
        this.gloalListener = listener;
    }

    private AuthenticationService authenticationService;
    private SearchService searchService;
    private UserAppointmentsService userAppointmentsService;
    private MapService mapService;


    private void initServices(String hostUrl){
        authenticationService = new AuthenticationServiceImpl(hostUrl);
        searchService = new SearchServiceImpl(hostUrl);
        userAppointmentsService = new UserAppointmentsServiceImpl(hostUrl);
        mapService = new MapServiceImpl(hostUrl);
    }

    public void authenticate(String username, String password, int requestID, ResultListener resultListener){
        Listeners listeners= new Listeners();
        listeners.addListener(resultListener);
        listeners.addListener(gloalListener);
        listeners.callOnStart(requestID);
        authenticationService.signIn(username, password, listeners, requestID);
    }

    public void searchClinics(String value, int offset, int limit, int requestID, ResultListener resultListener){
        Listeners listeners= new Listeners();
        listeners.addListener(resultListener);
        listeners.addListener(gloalListener);
        listeners.callOnStart(requestID);

        searchService.searchClinicsByName(value, offset, limit, listeners, requestID);
    }


    public void searchDoctors(String value, int offset, int limit, int requestID, ResultListener resultListener){
        Listeners listeners= new Listeners();
        listeners.addListener(resultListener);
        listeners.addListener(gloalListener);
        listeners.callOnStart(requestID);

        searchService.searchDoctorsByName(value, offset, limit, listeners, requestID);
    }
    public void getUserAppointments(int requestID, ResultListener resultListener){
        Listeners listeners= new Listeners();
        listeners.addListener(resultListener);
        listeners.addListener(gloalListener);
        listeners.callOnStart(requestID);

        userAppointmentsService.getAppointments(currentUser.token, listeners, requestID);
    }

    public boolean hasAuthorityUser(){
        return currentUser.userAuthority;
    }

    public boolean hasAuthorityDoctor(){
        return currentUser.doctorAuthority;
    }

    public void setCurrentUser(DataObject.CurrentUser user){
        if(user == null){
            currentUser = new DataObject.CurrentUser();
        }else {
            currentUser = user;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_USERNAME, currentUser.username);
        editor.putString(USER_ACCESS_TOKEN, currentUser.token);
        editor.putString(USER_FIRST_NAME, currentUser.firstName);
        editor.putString(USER_LAST_NAME, currentUser.lastName);
        editor.putString(USER_MIDDLE_NAME, currentUser.middleName);
        editor.putBoolean(USER_AUTHORITY_USER, currentUser.userAuthority);
        editor.putBoolean(USER_AUTHORITY_DOCTOR, currentUser.doctorAuthority);
        editor.apply();

    }

    public DataObject.CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void getClinicsLocations(int requestId, ResultListener resultListener){
        Listeners listeners= new Listeners();
        listeners.addListener(resultListener);
        listeners.addListener(gloalListener);
        listeners.callOnStart(requestId);
        mapService.loadAllClinicsLocations(requestId, listeners);
    }
}
