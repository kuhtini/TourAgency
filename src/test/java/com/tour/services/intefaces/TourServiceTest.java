package com.tour.services.intefaces;

import com.tour.model.Tour;
import com.tour.services.TourService;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.tour.services")
public class TourServiceTest {

    @Autowired
    private TourService tourService;

    private Tour tour =new Tour();


    @Before
    public void init() {
        tour.setName("tur");
        tour.setByDate (DateUtil.now());
        tour.setFromDate(DateUtil.yesterday());


    }

    @Test
    public void addTour() throws Exception {
        tourService.saveTour(tour);
        assertEquals(tour,tourService.getTourById(tour.getId()));
    }

    @Test
    public void getAllTours() throws Exception {
        tourService.saveTour(tour);
        assertEquals(Arrays.asList(tour),tourService.getAllTours());
    }

    @Test
    public void deleteAll() throws Exception {
        tourService.saveTour(tour);
        tourService.deleteTour(tour);
        assertEquals(new ArrayList<Tour>(),tourService.getAllTours());
    }





}