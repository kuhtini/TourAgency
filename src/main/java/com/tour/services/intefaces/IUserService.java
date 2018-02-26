package com.tour.services.intefaces;

import com.tour.model.enums.UserRole;
import java.util.List;

public interface IUserService<T>{

    void addNewUser(T user);

    void deleteUser(T user);

    T getUserByUserName(String userName);

    T getUserByEmail(String email);

    List<T> getAllUsers();

    List<T> getAllUsersByUserRole(UserRole userRole);

    List<T> getUserLikeByEmail(String email);

    List<T> getUserLikeByUserName(String userName);

    T getUserById(long id);

    List<T> getUsersByLastName(String lastName);


}
