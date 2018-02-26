package com.tour.repository;

import com.tour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByByDateAndFromDate(Date byDate, Date fromDate);

    List<Tour> findByByDate(Date fromDate);

    List<Tour> findByFromDate(Date fromDate);

    List<Tour> findByStatus(Tour.TourStatus status);


}
