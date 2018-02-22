package com.tour.repository;

import com.tour.model.Group;
import com.tour.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {


    List<Group> findByGuide(Guide guide);

}
