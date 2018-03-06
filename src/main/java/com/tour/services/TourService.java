package com.tour.services;

import com.tour.model.Tour;

import java.util.Date;
import java.util.List;
public interface TourService {


    void saveTour(Tour tour);

    void addNewTour(Tour tour);

    void deleteTour(Tour tour);

    void deleteTourById(long id);

    List<Tour> getToursByByDate(Date byDate);

    List<Tour> getToursByFromDate(Date fromDate);

    List<Tour> getToursByByDateAndFromDate(Date byDate, Date fromDate);

    List<Tour> getToursByStatus(Tour.TourStatus status);

    List<Tour> getAllTours();

    Tour getTourById(long id);

    void deleteAll();



}
