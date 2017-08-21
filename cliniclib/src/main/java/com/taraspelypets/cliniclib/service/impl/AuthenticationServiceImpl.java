package com.taraspelypets.cliniclib.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.Listeners;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.cliniclib.service.AuthenticationService;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 04.08.2017.
 */

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String TAG = "AuthenticationService";


    private String hostUrl;

    public AuthenticationServiceImpl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    @Override
    public void signIn(String login, String password, Listeners listener, int requestId) {
        Log.d(TAG, "signin()");

        String request = "";
        request += hostUrl;
        request += "/rest/auth";
        request += "?email=" + login;
        request += "&password=" + password;

        new SignInAsyncTask(listener, requestId).execute(request);
    }

    private class SignInAsyncTask extends AsyncTask<String, Object, List<DataObject>>{
        Listeners listener;
        int requestId;

        SignInAsyncTask(Listeners listener, int requestId) {
            this.listener = listener;
            this.requestId = requestId;
        }

        @Override
        protected List<DataObject> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground");


            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            try{
                DataObject.TokenAuthentication result = restTemplate.getForObject(strings[0], DataObject.TokenAuthentication.class);

                Log.d(TAG, "Token: " + result.token);
                Log.d(TAG, "Doctor: " + result.isDoctor);
                Log.d(TAG, "User: " + result.isUser);


                DataObject.CurrentUser user = new DataObject.CurrentUser();
                user.token = result.token;
                user.doctorAuthority = result.isDoctor;
                user.userAuthority = result.isUser;
                user.username = result.username;
                user.firstName = result.firstName;
                user.lastName = result.lastName;
                user.middleName = result.middleName;

                List<DataObject> res = new ArrayList<>();
                res.add(user);
                listener.callOnSuccess(res, requestId);
                return res;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DataObject> objects) {

            if(objects != null){
                listener.callOnSuccess(objects, requestId);
            } else{
                Log.d(TAG, "authentication failed");
                listener.callOnFailure(null, requestId);
            }
        }
    }
}
