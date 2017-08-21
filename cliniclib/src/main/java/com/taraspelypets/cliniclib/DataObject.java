package com.taraspelypets.cliniclib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Taras on 06.08.2017.
 */

public interface DataObject {

    class CurrentUser implements DataObject{



        public String username;
        public String firstName;
        public String lastName;
        public String middleName;
        public String token;
        public boolean userAuthority;
        public boolean doctorAuthority;
    }



    class Clinic implements DataObject, Parcelable {
        public long id;
        public String name;
        public String description;
        public String photo;

        public Contacts contacts;

        public Clinic(){

        }


        protected Clinic(Parcel in) {
            id = in.readLong();
            name = in.readString();
            description = in.readString();
            photo = in.readString();
            contacts = in.readParcelable(Contacts.class.getClassLoader());
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(name);
            parcel.writeString(description);
            parcel.writeString(photo);
            parcel.writeParcelable(contacts, i);
        }
    }


    class Doctor implements DataObject, Parcelable{
        public long id;
        public String firstName;
        public String lastName;
        public String middleName;
        public String specialisation;
        public String description;
        public String photo;

        public Contacts contacts;

        public long clinicId;
        public String clinicName;

        public Doctor(){

        }


        protected Doctor(Parcel in) {
            id = in.readLong();
            firstName = in.readString();
            lastName = in.readString();
            middleName = in.readString();
            specialisation = in.readString();
            description = in.readString();
            photo = in.readString();
            contacts = in.readParcelable(Contacts.class.getClassLoader());
            clinicId = in.readLong();
            clinicName = in.readString();
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



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(firstName);
            parcel.writeString(lastName);
            parcel.writeString(middleName);
            parcel.writeString(specialisation);
            parcel.writeString(description);
            parcel.writeString(photo);
            parcel.writeParcelable(contacts, i);
            parcel.writeLong(clinicId);
            parcel.writeString(clinicName);
        }
    }

    class Contacts implements DataObject, Parcelable {

        public String address;
        public String district;
        public String city;
        public String zipCode;
        public List<String> phones;
        public String email;
        public Double longitude;
        public Double latitude;

        public Contacts(){}

        protected Contacts(Parcel in) {
            address = in.readString();
            district = in.readString();
            city = in.readString();
            zipCode = in.readString();
            phones = in.createStringArrayList();
            email = in.readString();
        }

        public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
            @Override
            public Contacts createFromParcel(Parcel in) {
                return new Contacts(in);
            }

            @Override
            public Contacts[] newArray(int size) {
                return new Contacts[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(address);
            parcel.writeString(district);
            parcel.writeString(city);
            parcel.writeString(zipCode);
            parcel.writeStringList(phones);
            parcel.writeString(email);
        }
    }


    public class Appointment implements DataObject, Parcelable {

        public long appointmentDate;
        public boolean status;
        public double duration;

        public long patientId;
        public String patientFirstName;
        public String patientLastName;
        public String patientMiddleName;

        public long doctorId;
        public String doctorFirstName;
        public String doctorLastName;
        public String doctorMiddleName;
        public String doctorSpecialisation;

        public long clinicId;
        public String clinicName;


        public Appointment(){}
        protected Appointment(Parcel in) {
            appointmentDate = in.readLong();
            status = in.readByte() != 0;
            duration = in.readDouble();
            patientId = in.readLong();
            patientFirstName = in.readString();
            patientLastName = in.readString();
            patientMiddleName = in.readString();
            doctorId = in.readLong();
            doctorFirstName = in.readString();
            doctorLastName = in.readString();
            doctorMiddleName = in.readString();
            doctorSpecialisation = in.readString();
            clinicId = in.readLong();
            clinicName = in.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(appointmentDate);
            parcel.writeByte((byte) (status ? 1 : 0));
            parcel.writeDouble(duration);
            parcel.writeLong(patientId);
            parcel.writeString(patientFirstName);
            parcel.writeString(patientLastName);
            parcel.writeString(patientMiddleName);
            parcel.writeLong(doctorId);
            parcel.writeString(doctorFirstName);
            parcel.writeString(doctorLastName);
            parcel.writeString(doctorMiddleName);
            parcel.writeString(doctorSpecialisation);
            parcel.writeLong(clinicId);
            parcel.writeString(clinicName);
        }
    }



    class TokenAuthentication implements DataObject, Parcelable {

        public String token;
        public String username;

        public String firstName;
        public String lastName;
        public String middleName;

        public boolean isUser;
        public boolean isDoctor;

        public TokenAuthentication(){ }

        protected TokenAuthentication(Parcel in) {
            token = in.readString();
            username = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            middleName = in.readString();
            isUser = in.readByte() != 0;
            isDoctor = in.readByte() != 0;
        }

        public static final Creator<TokenAuthentication> CREATOR = new Creator<TokenAuthentication>() {
            @Override
            public TokenAuthentication createFromParcel(Parcel in) {
                return new TokenAuthentication(in);
            }

            @Override
            public TokenAuthentication[] newArray(int size) {
                return new TokenAuthentication[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(token);
            parcel.writeString(username);
            parcel.writeString(firstName);
            parcel.writeString(lastName);
            parcel.writeString(middleName);
            parcel.writeByte((byte) (isUser ? 1 : 0));
            parcel.writeByte((byte) (isDoctor ? 1 : 0));
        }
    }

    class ClinicLatLng implements DataObject, Parcelable{
        public long id;
        public String name;
        public double lat;
        public double lng;

        public ClinicLatLng(){}

        protected ClinicLatLng(Parcel in) {
            id = in.readLong();
            name = in.readString();
            lat = in.readDouble();
            lng = in.readDouble();
        }

        public static final Creator<ClinicLatLng> CREATOR = new Creator<ClinicLatLng>() {
            @Override
            public ClinicLatLng createFromParcel(Parcel in) {
                return new ClinicLatLng(in);
            }

            @Override
            public ClinicLatLng[] newArray(int size) {
                return new ClinicLatLng[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(name);
            parcel.writeDouble(lat);
            parcel.writeDouble(lng);
        }
    }


}
