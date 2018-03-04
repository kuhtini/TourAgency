package com.tour.repository;

import com.tour.model.Group;
import com.tour.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TouristRepository extends JpaRepository<Tourist, Long>, BaseUserMethods<Tourist> {

    List<Tourist> findByGroups(Group group);



}
