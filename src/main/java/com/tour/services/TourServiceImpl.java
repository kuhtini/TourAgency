package com.tour.services;

import com.tour.model.Tour;
import com.tour.repository.TourRepository;
import com.tour.services.intefaces.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TourServiceImpl implements TourService {


    private TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void addTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteTour(Tour tour) {
        tourRepository.delete(tour);
    }

    public void deleteTourById(long id) {
        tourRepository.delete(id);
    }

    public List<Tour> getToursByByDate(Date byDate) {
        return tourRepository.findByByDate(byDate);
    }

    public List<Tour> getToursByFromDate(Date fromDate) {
        return tourRepository.findByFromDate(fromDate);
    }

    public List<Tour> getToursByByDateAndFromDate(Date byDate, Date fromDate) {
        return tourRepository.findByByDateAndFromDate(byDate, fromDate);
    }

    public List<Tour> getToursByStatus(Tour.TourStatus status) {
        return tourRepository.findByStatus(status);
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(long id) {
        return tourRepository.findOne(id);
    }
}
