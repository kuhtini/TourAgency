package com.tour.service.impl;

import com.tour.model.Group;
import com.tour.model.Tour;
import com.tour.repository.TourRepository;
import com.tour.service.GroupService;
import com.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
//@Transactional
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    private final GroupService groupService;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, GroupService groupService) {
        this.tourRepository = tourRepository;
        this.groupService = groupService;
    }


    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tour addNewTour(Tour tour) {

        Group group = new Group();
        group.setTour(tour);
        tourRepository.save(tour);
        groupService.addGroup(group);

        //TODO ExceptionIfExist

        return tour;
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

    public void deleteAll() {
        tourRepository.deleteAll();
    }

    public Tour getTourById(long id) {
        return tourRepository.findOne(id);
    }

}
