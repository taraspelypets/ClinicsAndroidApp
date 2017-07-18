package com.taraspelypets.clinics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taraspelypets.clinics.entity.Appointment;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.entity.Contact;
import com.taraspelypets.clinics.entity.Doctor;

import java.text.SimpleDateFormat;

/**
 * Created by Taras on 15.07.2017.
 */

public class PAppointmentInfoFragment extends Fragment implements OnMapReadyCallback{

    public static final String APPOINTMENT = "appointment";

    private TextView mTextViewTime;

    private TextView mTextViewDoctorName;
    private TextView mTextViewDoctorSpecialisation;
    private TextView mTextViewDoctorPhoneNumbers;
    private TextView mTextViewDoctorEmail;

    private TextView mTextViewClinicName;
    private TextView mTextViewClinicPhoneNumbers;
    private TextView mTextViewClinicEmail;
    private TextView mTextViewClinicAddress;

    private MyMapView mMapView;
    private GoogleMap googleMap;

    private Appointment appointment;
    private Doctor doctor;
    private Contact doctorContact;
    private Clinic clinic;
    private Contact clinicContact;

    private double latitude;
    private double longitude;


    private boolean isCoordinatesPresent;

    public static PAppointmentInfoFragment newInstance(Appointment appointment) {
        PAppointmentInfoFragment fragment = new PAppointmentInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(APPOINTMENT, appointment);

        fragment.setArguments(args);
        return fragment;
    }

    private void initViews(View parent){
        mTextViewTime = parent.findViewById(R.id.textView_date_and_time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String timeAndDate = formatter.format(appointment.getAppintmentDate());
        mTextViewTime.setText(timeAndDate);


        mTextViewDoctorName = parent.findViewById(R.id.textView_doctor_name);
        mTextViewDoctorName.setText(doctor.getLastname() + " " + doctor.getFirstname() + " " + doctor.getMiddlename());

        mTextViewDoctorSpecialisation = parent.findViewById(R.id.textView_doctor_specialisation);

        mTextViewDoctorPhoneNumbers = parent.findViewById(R.id.textView_doctor_phone_numbers);
        mTextViewDoctorPhoneNumbers.setText(getPhonesString(doctorContact));

        mTextViewDoctorEmail = parent.findViewById(R.id.textView_doctor_email);
        mTextViewDoctorEmail.setText(doctorContact.getEmail());


        mTextViewClinicName = parent.findViewById(R.id.textView_clinic_name);
        mTextViewClinicName.setText(clinic.getClinic_name());

        mTextViewClinicPhoneNumbers = parent.findViewById(R.id.textView_clinic_phone_numbers);
        mTextViewClinicPhoneNumbers.setText(getPhonesString(clinicContact));

        mTextViewClinicEmail = parent.findViewById(R.id.textView_clinic_email);
        mTextViewClinicEmail.setText(clinicContact.getEmail());

        mTextViewClinicAddress = parent.findViewById(R.id.textView_clinic_address);
        mTextViewClinicAddress.setText(clinicContact.getAddress());
    }

    private void initMap(View parent){

        FrameLayout frameLayout = (FrameLayout) parent.findViewById(R.id.mapHoder);
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                metrics.widthPixels*3/4);
        mMapView.setLayoutParams(params);
        frameLayout.addView(mMapView);
        mMapView.getMapAsync(this);

    }

    private String getPhonesString(Contact contact){
        String phones = "";
        if(contact.getFirstPhone()!=null){
            phones += contact.getFirstPhone();
        }
        if(contact.getSecondPhone()!=null){
            phones += "\n" + contact.getSecondPhone();
        }
        if(contact.getThirdPhone()!=null){
            phones += "\n" + contact.getThirdPhone();
        }
        return phones;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_appointment_info, container, false);
        initViews(v);
        if(isCoordinatesPresent){
            initMap(v);
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            appointment = getArguments().getParcelable(APPOINTMENT);
            doctor = appointment.getDoctor();
            doctorContact = doctor.getContact();
            clinic = doctor.getClinic();
            clinicContact = clinic.getContact();

            latitude = clinicContact.getLatitude();
            longitude = clinicContact.getLongitude();
            if(latitude != 0 && longitude != 0){
                isCoordinatesPresent = true;
                GoogleMapOptions options = new GoogleMapOptions();
                options.mapType(GoogleMap.MAP_TYPE_TERRAIN)
                        .compassEnabled(false)
                        .rotateGesturesEnabled(false)
                        .tiltGesturesEnabled(false);
                mMapView = new MyMapView(getActivity(), options);
                mMapView.onCreate(savedInstanceState);
            }
        }


    }

    @Override
    public void onStart() {
        if(isCoordinatesPresent){
            mMapView.onStart();

        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if(isCoordinatesPresent){
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(isCoordinatesPresent){
            mMapView.onPause();
        }
        super.onPause();

    }

    @Override
    public void onStop() {
        if(isCoordinatesPresent){
            mMapView.onStop();
        }
        super.onStop();

    }

    @Override
    public void onDestroy() {
        if(isCoordinatesPresent){
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(isCoordinatesPresent){
            mMapView.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        if(isCoordinatesPresent){
            mMapView.onLowMemory();
        }
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));
       goToPosition(latitude, longitude);

    }

    private void goToPosition(double latitude, double longitude){
        try {
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }catch (Exception e){

        }

    }


}
