package com.taraspelypets.clinics.service.impl;

import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;

import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.service.SearchService;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Taras on 17.07.2017.
 */

public class SearchServiceImpl implements SearchService {
    private final String url = "http://10.4.49.241:8080/MvcAnnotationConf2-1.0-SNAPSHOT/firstclinic";
    @Override
    public void searchClinicsByName(String request, int offset, int limit, OnResultListener listener, int requestId) {
        LoaderAsyncTask loaderAsyncTask = new LoaderAsyncTask();
        loaderAsyncTask.execute(url);
    }

    private class LoaderAsyncTask extends AsyncTask<String, Void, List<Clinic>>{

        @Override
        protected List<Clinic> doInBackground(String... strings) {
            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            Clinic clinic = restTemplate.getForObject(strings[0], Clinic.class);

            Log.d("cliniccc", clinic.getClinic_name());

//            return Arrays.asList(forNow);
            return null;
        }

        @Override
        protected void onPostExecute(List<Clinic> clinics) {
            super.onPostExecute(clinics);

        }
    }

    @Override
    public void searchDoctorsByName(String request, int offset, int limit, OnResultListener listener, int requestId) {

    }


}
