package com.taraspelypets.clinics.service;

import com.taraspelypets.clinics.entity.BaseEntity;

import java.util.List;

/**
 * Created by Taras on 15.07.2017.
 */

public interface SearchService {

    public  void searchClinicsByName(String request, int offset, int limit, OnResultListener listener, int requestId);

    public void searchDoctorsByName(String request, int offset, int limit, OnResultListener listener, int requestId);

    public interface OnResultListener {
        void onResult(List<BaseEntity> entityList, int requestId);
    }
}
