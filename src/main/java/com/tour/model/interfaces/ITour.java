package com.tour.model.interfaces;

import com.tour.model.Tour;

import java.util.Date;
import java.util.List;

public interface ITour extends IdContain {

    String getName();

    Date getFromDate();

    Date getByDate();

    Tour.TourStatus getStatus();

    String getStartCity();

    Tour.TourStatus getTourStatus();

    List<String> getCities();


}
