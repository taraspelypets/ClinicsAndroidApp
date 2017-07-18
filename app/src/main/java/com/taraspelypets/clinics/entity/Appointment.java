package com.taraspelypets.clinics.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 *
 */
public class Appointment extends BaseEntity implements Parcelable{

    private Date appintmentDate;
    private String status;
    private double duration;

    private Doctor doctor;

    private User users;

    public Appointment() {
    }

    protected Appointment(Parcel in) {
        status = in.readString();
        duration = in.readDouble();
        doctor = in.readParcelable(Doctor.class.getClassLoader());
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Date getAppintmentDate() {
        return appintmentDate;
    }

    public void setAppintmentDate(Date appintmentDate) {
        this.appintmentDate = appintmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeDouble(duration);
        parcel.writeParcelable(doctor, i);
    }
}
