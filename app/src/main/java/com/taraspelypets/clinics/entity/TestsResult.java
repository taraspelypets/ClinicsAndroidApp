package com.taraspelypets.clinics.entity;


import java.util.Date;

/**
 * Created by Taras on 11.07.2017.
 */
public class TestsResult extends BaseEntity {

    private Date date;
    private String description;

    private Test tests;

    private User user;

    public TestsResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTests() {
        return tests;
    }

    public void setTests(Test tests) {
        this.tests = tests;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
