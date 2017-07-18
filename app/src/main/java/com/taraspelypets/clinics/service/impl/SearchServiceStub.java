package com.taraspelypets.clinics.service.impl;

import com.taraspelypets.clinics.entity.BaseEntity;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.entity.Doctor;
import com.taraspelypets.clinics.service.SearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 15.07.2017.
 */

public class SearchServiceStub implements SearchService {
    public void searchClinicsByName(String request, int offset, int limit, OnResultListener listener, int requestId){
        List<BaseEntity> results = new ArrayList<>();

        Clinic clinic = new Clinic();
        clinic.setClinic_name("Hospotal");
        clinic.setDescription("Метод dsgsdfhfhdghghjhdgjfhhfsdsdjghfatsyjhgfdghgfdddsgfdgdsfggetView должен возвращать View пункта списка. Для этого мы создавали layout-ресурс R.layout.item. В этом методе мы должны из R.layout.item создать View, заполнить его данными и отдать списку. Но перед тем как создавать, мы пробуем использовать convertView, который идет на вход метода. Это уже созданное ранее View, но неиспользуемое в данный момент. Например, пр");
        clinic.setPhoto("FSDHRDTHGHDFJDFJHNGFDNGDHNYDGHFBYTDJGNHHSGRHGBFJNGFBSF");
        results.add(clinic);
        Clinic clinic2 = new Clinic();
        clinic2.setClinic_name("Krankenhouse");
        clinic2.setPhoto("FSDHRDTHGHDFJDFJHNGFDNGDHNYDGHFBYTDJGNHHSGRHGBFJNGFBSF");
        results.add(clinic2);

        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);
        results.add(clinic2);

        listener.onResult(results, requestId);

    }

    public void searchDoctorsByName(String request, int offset, int limit, OnResultListener listener, int requestId){
        List<BaseEntity> results = new ArrayList<>();
        Doctor doctor = new Doctor();
        doctor.setLastname("Olifir");
        doctor.setFirstname("Ivan");
        doctor.setMiddlename("Mykolayovych");
        results.add(doctor);

        Doctor doctor1 = new Doctor();
        doctor1.setLastname("Savinkov");
        doctor1.setFirstname("Mykola");
        doctor1.setMiddlename("Mykolayovych");
        results.add(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setLastname("Sekh");
        doctor2.setFirstname("Sofia");
        doctor2.setMiddlename("Bogdanivna");
        results.add(doctor2);

        Doctor doctor3 = new Doctor();
        doctor3.setLastname("Mazur");
        doctor3.setFirstname("Julia");
        doctor3.setMiddlename("Stepanivna");
        results.add(doctor3);


        Doctor doctor4 = new Doctor();
        doctor4.setLastname("Stachiv");
        doctor4.setFirstname("Gregorii");
        doctor4.setMiddlename("Pavlovich");
        results.add(doctor4);

        listener.onResult(results, requestId);
    }

}
