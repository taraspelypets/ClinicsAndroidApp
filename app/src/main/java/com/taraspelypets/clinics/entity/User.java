package com.taraspelypets.clinics.entity;

import java.util.List;

public class User extends BaseEntity {

    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private String password;
    private String photo;

    private List<Appointment> appointments;

    private List<Role> roles;

    private Contact contact;

    private List<MedicalCard> medicalCards;

    private List<TestsResult> testsResults;


    public User() {
    }

    public List<TestsResult> getTestsResults() {
        return testsResults;
    }

    public void setTestsResults(List<TestsResult> testsResults) {
        this.testsResults = testsResults;
    }

    public List<MedicalCard> getMedicalCards() {
        return medicalCards;
    }

    public void setMedicalCards(List<MedicalCard> medicalCards) {
        this.medicalCards = medicalCards;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
