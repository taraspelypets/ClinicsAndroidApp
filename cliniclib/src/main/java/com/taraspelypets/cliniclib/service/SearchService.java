package com.taraspelypets.cliniclib.service;

import com.taraspelypets.cliniclib.Listeners;
import com.taraspelypets.cliniclib.ResultListener;

import java.util.List;

/**
 * Created by Taras on 15.07.2017.
 */

public interface SearchService {

    public  void searchClinicsByName(String request, int offset, int limit, Listeners listeners, int requestId);

    public  void searchDoctorsByName(String request, int offset, int limit, Listeners listeners, int requestId);

}
