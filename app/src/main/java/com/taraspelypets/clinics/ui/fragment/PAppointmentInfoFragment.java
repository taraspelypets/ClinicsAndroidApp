package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;

import com.taraspelypets.clinics.ui.view.MyMapView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Taras on 15.07.2017.
 */

public class PAppointmentInfoFragment extends Fragment implements View.OnClickListener{

    public static final String APPOINTMENT = "appointment";

    private ImageButton mButtonBack;

    private TextView mTextViewTime;

    private TextView mTextViewDoctorName;
    private TextView mTextViewDoctorSpecialisation;

    private TextView mTextViewClinicName;


    DataObject.Appointment appointment;


    public static PAppointmentInfoFragment newInstance(DataObject.Appointment appointment) {
        PAppointmentInfoFragment fragment = new PAppointmentInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(APPOINTMENT, appointment);

        fragment.setArguments(args);
        return fragment;
    }

    private void initViews(View parent){

        mButtonBack = parent.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mTextViewTime = parent.findViewById(R.id.textView_date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String timeAndDate = formatter.format(new Date(appointment.appointmentDate));
        mTextViewTime.setText(timeAndDate);

        mTextViewDoctorName = parent.findViewById(R.id.textView_doctor_name);
        String doctorName = appointment.doctorLastName + " " + appointment.doctorFirstName + " " + appointment.doctorMiddleName;
        mTextViewDoctorName.setText(doctorName);

        mTextViewDoctorSpecialisation = parent.findViewById(R.id.textView_doctor_specialisation);
        mTextViewDoctorSpecialisation.setText(appointment.doctorSpecialisation);

        mTextViewClinicName = parent.findViewById(R.id.textView_clinic_name);
        mTextViewClinicName.setText(appointment.clinicName);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_appointment_info, container, false);
        initViews(v);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            appointment = getArguments().getParcelable(APPOINTMENT);

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
        }
    }

}
