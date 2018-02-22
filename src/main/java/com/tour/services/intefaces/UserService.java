package com.tour.services.intefaces;

import com.tour.enums.UserRole;
import java.util.List;

public interface UserService<T>{

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
