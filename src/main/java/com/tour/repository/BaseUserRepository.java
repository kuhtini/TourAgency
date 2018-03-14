package com.tour.repository;

import com.tour.model.BaseUser;
import com.tour.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseUserRepository extends JpaRepository<BaseUser,Long> , BaseUserMethods<BaseUser> {

    List<BaseUser> findByLastName(String lastName);


    List<BaseUser> findByUserNameLike(String userName);


    BaseUser findByUserName(String userName);


    List<BaseUser> findByRoles(UserRole userRole);


    List<BaseUser> findByEmailLike(String email);


    BaseUser findByEmail(String email);
}
