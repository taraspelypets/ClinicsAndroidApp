package com.taraspelypets.cliniclib.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.Listeners;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.cliniclib.service.SearchService;


import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 17.07.2017.
 */

public class SearchServiceImpl implements SearchService {
    private String url;


    public SearchServiceImpl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void searchClinicsByName(String request, int offset, int limit, Listeners listeners, int requestId) {
        String fullUrl = url;
        fullUrl += "/rest/search/clinics";
        fullUrl += "?value=" + request;
        fullUrl += "&offset=" + offset;
        fullUrl += "&limit=" + limit;

        listeners.callOnStart(requestId);
        ClinicLoaderAsyncTask loaderAsyncTask = new ClinicLoaderAsyncTask(listeners, requestId);
        loaderAsyncTask.execute(fullUrl);
    }

    @Override
    public void searchDoctorsByName(String request, int offset, int limit, Listeners listeners, int requestId) {
        String fullUrl = url;
        fullUrl += "/rest/search/doctors";
        fullUrl += "?value=" + request;
        fullUrl += "&offset=" + offset;
        fullUrl += "&limit=" + limit;

        DoctorLoaderAsyncTask loaderAsyncTask = new DoctorLoaderAsyncTask(listeners, requestId);
        loaderAsyncTask.execute(fullUrl);
    }


    private class ClinicLoaderAsyncTask extends AsyncTask<String, Void, List<DataObject>>{

        private Listeners listeners;
        private int requestId;

        ClinicLoaderAsyncTask(Listeners listeners , int requestId){
            this.listeners = listeners;
            this.requestId = requestId;
        }

        @Override
        protected List<DataObject> doInBackground(String... strings) {
            Log.d("SearchServ", strings[0]);
            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            DataObject.Clinic[] clinics;
            try{
                clinics = restTemplate.getForObject(strings[0], DataObject.Clinic[].class);
            } catch (Exception e){
                clinics = new DataObject.Clinic[0];
            }
            List<DataObject> objects = new ArrayList<>();
            for (DataObject.Clinic c: clinics){
                objects.add(c);
            }

            return objects;
        }

        @Override
        protected void onPostExecute(List<DataObject> clinics) {
            listeners.callOnSuccess(clinics, requestId);
        }
    }

    private class DoctorLoaderAsyncTask extends AsyncTask<String, Void, List<DataObject>>{

        private Listeners listeners;
        private int requestId;

        DoctorLoaderAsyncTask(Listeners listeners, int requestId){
            this.listeners = listeners;
            this.requestId = requestId;
        }

        @Override
        protected List<DataObject> doInBackground(String... strings) {
            Log.d("SearchServ", strings[0]);
            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            DataObject.Doctor[] doctor;
            try{
                doctor = restTemplate.getForObject(strings[0], DataObject.Doctor[].class);
            } catch (Exception e){
                doctor = new DataObject.Doctor[0];
            }
            List<DataObject> objects = new ArrayList<>();
            for (DataObject.Doctor c: doctor){
                objects.add(c);
            }

            return objects;
        }

        @Override
        protected void onPostExecute(List<DataObject> doctors) {
            listeners.callOnSuccess(doctors, requestId);
        }
    }






}
