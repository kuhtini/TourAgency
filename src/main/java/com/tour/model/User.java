package com.tour.model;

import java.util.Set;


public class User extends BaseUser {

    private UserType userType;


    public User(String userName, String password, String firstName, String lastName, boolean active, String email, String confirmPassword, Set<Role> roles, UserType userType) {
        super(userName, password, firstName, lastName, active, email, confirmPassword, roles);
        this.userType = userType;
    }

    public User() {

    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
