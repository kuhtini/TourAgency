package com.tour.service.intefaces;


import com.tour.model.enums.UserRole;

import java.io.Serializable;
import java.util.List;

public interface IUserService<T, ID extends Serializable> {

    T saveUser(T user);

    T addNewUser(T user);

    void deleteUser(T user);

    T getUserByUserName(String userName);

    T getUserByEmail(String email);

    List<T> getAllUsers();

    List<T> getUserLikeByEmail(String email);

    List<T> getUserLikeByUserName(String userName);

    T getUserById(ID id);

    List<T> getUsersByLastName(String lastName);

    void deleteUser(ID id);

    void deleteAll();

    List<T> getAllUsersByUserRole(UserRole userRole);

    boolean isInGroup(long guideID, long tourId);
}
