package com.tour.services.intefaces;

import com.tour.model.Tour;
import com.tour.services.TourService;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;


import static com.tour.utils.Creator.nextTour;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TourServiceTest {

    @Autowired
    private TourService tourService;

    private Tour tour = new Tour();


    @Before
    public void init() {

        tour = nextTour();
        tourService.saveTour(tour);

    }

    @Test
    public void addTour() throws Exception {

        assertEquals(tour, tourService.getTourById(tour.getId()));
    }

    @Test
    public void getAllTours() throws Exception {

        assertEquals(Arrays.asList(tour), tourService.getAllTours());
    }

    @Test
    public void deleteAll() throws Exception {
        tourService.deleteAll();
        assertEquals(new ArrayList<Tour>(), tourService.getAllTours());
    }


}