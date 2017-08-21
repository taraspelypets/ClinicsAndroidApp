package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.clinics.R;

import com.taraspelypets.clinics.ui.activity.FragmentRequestListener;
import com.taraspelypets.clinics.ui.listadapter.PatientAppointmentsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 15.07.2017.
 */

public class PatientAppointmentsFragment extends Fragment
        implements PatientAppointmentsAdapter.OnItemChosen, ResultListener, View.OnClickListener{

    private ImageButton mButtonBack;

    private FragmentRequestListener mListener;

    private RecyclerView mRecyclerAppointments;
    private ArrayList<DataObject.Appointment> appointments = new ArrayList<>();

    private PatientAppointmentsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_appointments, container, false);

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mRecyclerAppointments = v.findViewById(R.id.recyclerView_patient_appointments);
        mRecyclerAppointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAppointments.setItemAnimator(new DefaultItemAnimator());
        adapter =  new PatientAppointmentsAdapter(appointments, this);
        mRecyclerAppointments.setAdapter(adapter);

        ClinicLib.getInstance().getUserAppointments(0, this);
        return v;
    }

    @Override
    public void onItemChosen(Object entity) {
        if ( entity instanceof DataObject.Appointment){
            DataObject.Appointment appointment = (DataObject.Appointment)entity;
            if(mListener != null){
                mListener.onFragmentRequest(PAppointmentInfoFragment.newInstance(appointment));
            }
        }
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

    @Override
    public void onStartRequest(int requestId) {

    }

    @Override
    public void onSuccess(int requestId, List<DataObject> data) {
        Log.d("fr", "succ");
        appointments.clear();
        for (DataObject ob: data){
            appointments.add((DataObject.Appointment) ob);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(int requestId, DataObject error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
        }
    }
}
