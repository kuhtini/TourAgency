package com.tour.model.interfaces;

import com.tour.model.Group;
import com.tour.model.Tour;

import java.util.Date;
import java.util.List;

public interface ITour extends IdContain {

    String getName();

    Date getFromDate();

    Date getByDate();

    Tour.TourStatus getStatus();

    String getStartCity();

    List<String> getCities();

    int getPrice();

    String getDescription();

    List<Group> getGroups();

}
