package com.taraspelypets.cliniclib.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.Listeners;
import com.taraspelypets.cliniclib.service.MapService;

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
 * Created by Taras on 07.08.2017.
 */

public class MapServiceImpl implements MapService {
    private static final String TAG = "MapServiceImpl";

    private String hostUrl;

    public MapServiceImpl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    @Override
    public void loadAllClinicsLocations(int requestId, Listeners listeners) {
        String url = hostUrl;
        url += "/rest/clinics/map/all";

        new LoadAsyncTask(requestId, listeners).execute(url);
    }


    private class LoadAsyncTask extends AsyncTask<String, Object, List<DataObject>> {

        private int requestId;
        private Listeners listeners;

        public LoadAsyncTask(int requestId, Listeners listeners) {
            this.requestId = requestId;
            this.listeners = listeners;
        }

        @Override
        protected List<DataObject> doInBackground(String... strings) {
            Log.i(TAG, "Loading coordinates from " + strings[0]);

            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            DataObject.ClinicLatLng[] clinicLatLngs;
            try {
                clinicLatLngs = restTemplate.getForObject(strings[0], DataObject.ClinicLatLng[].class);
                List<DataObject> objects = new ArrayList<>();
                objects.addAll(Arrays.asList(clinicLatLngs));
                Log.i(TAG, "Loading success");
                return objects;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "Loading failure");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DataObject> objects) {
            if (objects != null) {
                listeners.callOnSuccess(objects, requestId);
            } else {
                listeners.callOnFailure(null, requestId);
            }
        }
    }


}
