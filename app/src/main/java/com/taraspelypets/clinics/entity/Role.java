package com.taraspelypets.clinics.entity;

import java.util.List;

/**
 * Created by kilopo on 11.07.2017.
 */

public class Role extends BaseEntity {

    private String name;

    private List<User> users;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
