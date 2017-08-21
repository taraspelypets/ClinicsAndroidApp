package com.taraspelypets.cliniclib.service.impl;

import android.os.AsyncTask;
import android.util.Log;


import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.Listeners;
import com.taraspelypets.cliniclib.service.UserAppointmentsService;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Taras on 05.08.2017.
 */

public class UserAppointmentsServiceImpl implements UserAppointmentsService {


    private String hostUrl;

    public UserAppointmentsServiceImpl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    @Override
    public void getAppointments(String token, Listeners listeners, int requestId) {
        String url = hostUrl;
        url += "/rest/api/user/appointments";
        url += "?token=" + token;
        new LoadAsyncTask(listeners, requestId).execute(url);

    }


    private class LoadAsyncTask extends AsyncTask<String, Object, List<DataObject>> {
        Listeners listeners;
        int requestId;

        public LoadAsyncTask(Listeners listeners, int requestId) {
            this.listeners = listeners;
            this.requestId = requestId;
        }

        @Override
        protected List<DataObject> doInBackground(String... strings) {
            Log.d("doIn", strings[0]);

            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            try{
                DataObject.Appointment[] result = restTemplate.getForObject(strings[0], DataObject.Appointment[].class);

                List<DataObject> objects= new ArrayList<>();
                objects.addAll(Arrays.asList(result));
                return objects;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<DataObject> data) {
            if(data != null){
                listeners.callOnSuccess(data, requestId);
            } else{
                listeners.callOnFailure(null, requestId);
            }
        }
    }

}
