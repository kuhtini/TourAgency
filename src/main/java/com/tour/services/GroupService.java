package com.tour.services;

import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;

import java.util.List;

public interface GroupService {

    Group addGroup(Group group);

    void deleteGroup(Group group);

    void deleteGroupById(long id);

    Group getGroupById(long id);

    List<Group> getGroupsByGuide(Guide guide);

    List<Group> getGroupsByTourist(Tourist tourist);

    List<Group> getGroupsByTour(Tour tour);

    List<Group> getAllGroups();


    void deleteAll();
}
