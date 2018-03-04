package com.tour.services.impl;

import com.tour.model.Group;
import com.tour.model.Tour;
import com.tour.repository.TourRepository;
import com.tour.services.GroupService;
import com.tour.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TourServiceImpl implements TourService {


    private TourRepository tourRepository;
    private GroupService groupService;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, GroupService groupService) {
        this.tourRepository = tourRepository;
        this.groupService = groupService;
    }

    public void saveTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void addNewTour(Tour tour) {

            Group group = new Group();
            group.setTour(tour);
            tourRepository.save(tour);
            groupService.addGroup(group);

        //TODO ExceptionIfExist


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

    public void joinInToGroup(Group group){

    }
}
