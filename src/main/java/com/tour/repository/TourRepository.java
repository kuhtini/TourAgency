package com.tour.repository;

import com.tour.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {


    @RestResource(exported = false)
    List<Tour> findByByDateAndFromDate(Date byDate, Date fromDate);

    @RestResource(exported = false)
    List<Tour> findByByDate(Date fromDate);

    @RestResource(exported = false)
    List<Tour> findByFromDate(Date fromDate);

    @RestResource(exported = false)
    List<Tour> findByStatus(Tour.TourStatus status);


    //Search
    @Query("select tour from Tour tour  where tour.status = 'ACTIVE' ")
    @RestResource(path = "active")
    Page<Tour> findActive(Pageable pageable);

    @RestResource(path = "fromDate", rel = "fromDate")
    Page<Tour> findPageByFromDate(@Param("fromDate") Date fromDate, Pageable pageable);

    @RestResource(path = "status", rel = "status")
    Page<Tour> findPageByStatus(@Param("status") Tour.TourStatus status, Pageable pageable);

    @RestResource(path = "byDate", rel = "byDate")
    Page<Tour> findPageByByDate(@Param("byDate") Date fromDate, Pageable pageable);

    @RestResource(path = "price", rel = "price")
    Page<Tour> findPageByPriceBetween(@Param("from") int from, @Param("to") int to, Pageable pageable);

    @RestResource(path = "priceLess", rel = "priceTo")
    Page<Tour> findPageByPriceLessThanEqual(@Param("to") int to, Pageable pageable);

    @RestResource(path = "priceFrom", rel = "priceFrom")
    Page<Tour> findPageByPriceGreaterThanEqual(@Param("from") int to, Pageable pageable);

    @RestResource(path = "name", rel = "name")
    Page<Tour> findPageByNameLike(@Param("name") String name,Pageable pageable);

}
