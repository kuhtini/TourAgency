package com.tour.model.interfaces;


import com.tour.model.BaseUser;

import java.util.Set;

public interface IUser extends IdContain {

    String getUserName();

    String getPassword();

    String getLastName();

    String getFirstName();

    Set<BaseUser.Role> getRoles();

    String getEmail();

    String getPhone();


}
