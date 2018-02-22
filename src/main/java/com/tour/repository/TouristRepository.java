package com.tour.repository;

import com.tour.enums.UserRole;
import com.tour.model.BaseUser;
import com.tour.model.Group;
import com.tour.model.Tourist;
import com.tour.model.interfaces.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TouristRepository extends JpaRepository<Tourist, Long>, BaseUserMethods<Tourist> {

    List<Tourist> findByGroups(Group group);


}
