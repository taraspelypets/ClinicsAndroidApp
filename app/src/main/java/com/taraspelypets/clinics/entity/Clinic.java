package com.taraspelypets.clinics.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 *
 */



public class Clinic extends BaseEntity implements Parcelable {
    private String clinic_name;
    private String photo;
    private String description;


    private Contact contact;

    public Clinic() {
    }

    protected Clinic(Parcel in) {
        clinic_name = in.readString();
        photo = in.readString();
        description = in.readString();
    }

    public static final Creator<Clinic> CREATOR = new Creator<Clinic>() {
        @Override
        public Clinic createFromParcel(Parcel in) {
            return new Clinic(in);
        }

        @Override
        public Clinic[] newArray(int size) {
            return new Clinic[size];
        }
    };

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contacts) {
        this.contact = contacts;
    }


    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
        parcel.writeString(clinic_name);
        parcel.writeString(photo);
        parcel.writeString(description);
    }
}
