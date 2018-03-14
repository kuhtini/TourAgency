package com.tour.repository;

import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {

    //@Query("select group from Group group where group.guide=:#{#guide}")
    List<Group> findByGuide(Guide guide);

    List<Group> findByTourists(Tourist tourist);

    List<Group> findByTour(Tour tour);


}
