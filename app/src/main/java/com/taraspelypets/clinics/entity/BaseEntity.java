package com.taraspelypets.clinics.entity;


public abstract class BaseEntity {

    private long id;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
