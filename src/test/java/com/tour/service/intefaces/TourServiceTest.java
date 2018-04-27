package com.tour.service.intefaces;

import com.tour.model.Tour;
import com.tour.service.TourService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tour.utils.Creator.nextTour;
import static org.easymock.EasyMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TourServiceTest {


    private TourService tourService = createNiceMock(TourService.class);

    private Tour tour = nextTour();


    @Before
    public void init() {

        tour = nextTour();
        expect(tourService.saveTour(tour)).andReturn(tour);
        replay();
        tourService.saveTour(tour);
        verify();
        reset(tourService);

    }

    @Test
    public void addTour() throws Exception {
        expect(tourService.getTourById(anyLong())).andReturn(tour);
        replay();
        tourService.getTourById(tour.getId());
        verify();
    }

    @Test
    public void getAllTours() throws Exception {
        expect(tourService.getAllTours()).andReturn(Arrays.asList(tour));
        replay();
        tourService.getAllTours();
        verify();
    }

    @Test
    public void deleteAll() throws Exception {
        tourService.deleteAll();
        expectLastCall().andVoid().times(1);
        expect(tourService.getAllTours()).andReturn(new ArrayList<>());
        replay();
        tourService.deleteAll();
        tourService.getAllTours();
        verify();


    }


}