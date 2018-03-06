package com.tour.repository;

import com.tour.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {


    List<Tour> findByByDateAndFromDate(@Param("byDate") Date byDate, @Param("fromDate")Date fromDate);

    @RestResource(path = "byDate")
    List<Tour> findByByDate(@Param("byDate")Date fromDate);

    @RestResource(path = "fromDate")
    List<Tour> findByFromDate(@Param("formDate") Date fromDate);

    @RestResource(path = "status")
    Page<Tour> findPageByStatus(@Param("status") Tour.TourStatus status ,Pageable pageable);

    List<Tour> findByStatus( Tour.TourStatus status);


    @Query("select tour from Tour tour  where tour.status = 'ACTIVE' ")
    @RestResource(path = "active")
    Page<Tour> findActive(Pageable pageable);




}
