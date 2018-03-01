package com.tour.rest;

import com.tour.model.Tour;
import com.tour.services.intefaces.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/tours")
public class TourRestControllerImpl implements TourRestController {

    private TourService tourService;


    @GetMapping
    private List<Tour> getTours(){
        return tourService.getAllTours();
    }

    @PutMapping
    private void addTour(@RequestParam Tour tour){
        tourService.addTour(tour);
    }


}
