package com.tour.model.interfaces;


import com.tour.model.Role;

import java.util.Set;

public interface IUser extends IdContain {

    String getUserName();

    String getPassword();

    String getLastName();

    String getFirstName();

    Set<Role> getRoles();

    String getEmail();

}
