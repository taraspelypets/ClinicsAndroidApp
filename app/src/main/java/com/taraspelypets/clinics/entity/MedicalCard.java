package com.taraspelypets.clinics.entity;

import java.util.Date;

/**
 *
 */
public class MedicalCard extends BaseEntity {
    private Date date;
    private String description;

    private User user;

    public MedicalCard() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "MedicalCard{" +
                "date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}