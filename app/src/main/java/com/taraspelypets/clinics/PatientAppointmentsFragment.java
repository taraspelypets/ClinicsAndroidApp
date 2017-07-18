package com.taraspelypets.clinics;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.clinics.entity.Appointment;
import com.taraspelypets.clinics.entity.BaseEntity;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.entity.Contact;
import com.taraspelypets.clinics.entity.Doctor;
import com.taraspelypets.clinics.listadapters.PatientAppointmentsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Taras on 15.07.2017.
 */

public class PatientAppointmentsFragment extends Fragment implements PatientAppointmentsAdapter.OnItemChosen{

    private FragmentRequestListener mListener;

    private RecyclerView mRecyclerAppointments;
    private ArrayList<Appointment> appointments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_appointments, container, false);
        populate();
        mRecyclerAppointments = v.findViewById(R.id.recyclerView_patient_appointments);
        mRecyclerAppointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAppointments.setItemAnimator(new DefaultItemAnimator());
        mRecyclerAppointments.setAdapter(new PatientAppointmentsAdapter(appointments, this));
        return v;
    }

    @Override
    public void onItemChosen(BaseEntity entity) {
        if ( entity instanceof Appointment){
            Appointment appointment = (Appointment)entity;
            if(mListener != null){
                mListener.onFragmentRequest(PAppointmentInfoFragment.newInstance(appointment));
            }
        }
    }

    private void populate(){
        Appointment appointment = new Appointment();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse("21/12/2012");
            appointment.setAppintmentDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Contact contact = new Contact();
        contact.setFirstPhone("+380631929109");
        contact.setSecondPhone("+380631145337");
        contact.setThirdPhone("+380631929109");
        contact.setAddress("Lviv, Pekarska str, 50");
        contact.setLatitude(49.836218);
        contact.setLongitude(24.035061);
        contact.setEmail("tarasp@outlook.com");

        Clinic clinic = new Clinic();
        clinic.setContact(contact);
        clinic.setClinic_name("Vet Academy");

        Doctor doctor = new Doctor();
        doctor.setClinics(clinic);
        doctor.setContact(contact);
        doctor.setFirstname("Taras");
        doctor.setLastname("Pelypets");
        doctor.setMiddlename("Igorovich");

        appointment.setDoctor(doctor);

        appointments.add(appointment);
   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentRequestListener) {
            mListener = (FragmentRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
