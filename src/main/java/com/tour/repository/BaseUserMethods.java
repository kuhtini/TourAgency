package com.tour.repository;

import com.tour.model.enums.UserRole;

import java.util.List;

public interface BaseUserMethods<T> {
    List<T> findByLastName(String lastName);

    List<T> findByUserNameLike(String userName);

    T findByUserName(String userName);

    List<T> findByRoles(UserRole userRole);

    List<T> findByEmailLike(String email);

    T findByEmail(String email);
}
