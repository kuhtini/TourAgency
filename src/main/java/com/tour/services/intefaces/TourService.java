package com.tour.services.intefaces;

import com.tour.model.Tour;

import java.util.Date;
import java.util.List;
public interface TourService {


    void addTour(Tour tour);

    void deleteTour(Tour tour);

    void deleteTourById(long id);

    List<Tour> getToursByByDate(Date byDate);

    List<Tour> getToursByFromDate(Date fromDate);

    List<Tour> getToursByByDateAndFromDate(Date byDate, Date fromDate);

    List<Tour> getToursByStatus(Tour.TourStatus status);

}
