package com.tour.services.intefaces;



import com.tour.model.Tourist;
import com.tour.model.enums.UserRole;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IUserService<T, ID extends Serializable>{

    void saveUser(T user);

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

    void addNewUser(T user);

    List<T> getAllUsersByUserRole(UserRole userRole);

}
