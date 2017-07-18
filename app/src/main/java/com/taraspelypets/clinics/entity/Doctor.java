package com.taraspelypets.clinics.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by User on 11.07.2017.
 */
public class Doctor extends User implements Parcelable {
    private String description;

    private Clinic clinics;

    private List<Appointment> docAppointments;

    public Doctor() {
    }

    protected Doctor(Parcel in) {
        description = in.readString();
        clinics = in.readParcelable(Clinic.class.getClassLoader());
        docAppointments = in.createTypedArrayList(Appointment.CREATOR);
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public List<Appointment> getDocAppointments() {
        return docAppointments;
    }

    public void setDocAppointments(List<Appointment> docAppointments) {
        this.docAppointments = docAppointments;
    }

    public Clinic getClinic() {
        return clinics;
    }

    public void setClinics(Clinic clinics) {
        this.clinics = clinics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeParcelable(clinics, i);
        parcel.writeTypedList(docAppointments);
    }
}
