package com.taraspelypets.clinics.listadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.entity.Appointment;
import com.taraspelypets.clinics.entity.BaseEntity;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.entity.Doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Taras on 15.07.2017.
 */

public class PatientAppointmentsAdapter extends RecyclerView.Adapter<PatientAppointmentsAdapter.MyViewHolder> {



    ArrayList<Appointment> objects;
    OnItemChosen onItemChosen;

    public PatientAppointmentsAdapter(ArrayList<Appointment> products, OnItemChosen onItemChosen) {
        objects = products;
        this.onItemChosen = onItemChosen;
    }

    public Appointment getItem(int position) {
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
        final Appointment appointment = getItem(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String timeAndDate = formatter.format(appointment.getAppintmentDate());

        Doctor doctor = appointment.getDoctor();
        String doctorName = doctor.getLastname() + " " + doctor.getFirstname() + " " + doctor.getMiddlename();
        Clinic clinic = doctor.getClinic();
        String clinicName = clinic.getClinic_name();
        String clinicAddress = clinic.getContact().getAddress();

        holder.textViewdateAndTime.setText(timeAndDate);
        holder.textViewDoctorName.setText(doctorName);
        holder.textViewClinicName.setText(clinicName);
        holder.textViewClinicAddress.setText(clinicAddress);


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
        void onItemChosen(BaseEntity entity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewdateAndTime;
        TextView textViewDoctorName;
        TextView textViewDoctorSpecialisation;
        TextView textViewClinicName;
        TextView textViewClinicAddress;

        public MyViewHolder(View view) {
            super(view);
            textViewdateAndTime = view.findViewById(R.id.textView_date_and_time);
            textViewDoctorName = view.findViewById(R.id.textView_doctor_name);
            textViewDoctorSpecialisation = view.findViewById(R.id.textView_doctor_specialisation);
            textViewClinicName = view.findViewById(R.id.textView_clinic_name);
            textViewClinicAddress = view.findViewById(R.id.textView_clinic_address);

        }
    }

}
