package com.tour.repository;

import com.tour.model.Group;
import com.tour.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long>, BaseUserMethods<Guide> {

    List<Guide> findByGroups(Group group);

}
