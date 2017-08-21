package com.taraspelypets.clinics.ui.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Taras on 15.07.2017.
 */

public class PatientAppointmentsAdapter extends RecyclerView.Adapter<PatientAppointmentsAdapter.MyViewHolder> {



    ArrayList<DataObject.Appointment> objects;
    OnItemChosen onItemChosen;

    public PatientAppointmentsAdapter(ArrayList<DataObject.Appointment> products, OnItemChosen onItemChosen) {
        objects = products;
        this.onItemChosen = onItemChosen;
    }

    public DataObject.Appointment getItem(int position) {
        return objects.get(position);
    }

    @Override
    public PatientAppointmentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new PatientAppointmentsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientAppointmentsAdapter.MyViewHolder holder, int position) {
        final DataObject.Appointment appointment = getItem(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String timeAndDate = formatter.format(new Date(appointment.appointmentDate));


        String doctorName = appointment.doctorLastName + " " + appointment.doctorFirstName + " " + appointment.doctorMiddleName;

        String clinicName = appointment.clinicName;

        holder.textViewDate.setText(timeAndDate);
        holder.textViewDoctorName.setText(doctorName);
        holder.textViewClinicName.setText(clinicName);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChosen.onItemChosen(appointment);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public interface OnItemChosen {
        void onItemChosen(Object entity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewDoctorName;
        TextView textViewDoctorSpecialisation;
        TextView textViewClinicName;

        public MyViewHolder(View view) {
            super(view);
            textViewDate = view.findViewById(R.id.textView_date);
            textViewDoctorName = view.findViewById(R.id.textView_doctor_name);
            textViewDoctorSpecialisation = view.findViewById(R.id.textView_doctor_specialisation);
            textViewClinicName = view.findViewById(R.id.textView_clinic_name);

        }
    }




}
