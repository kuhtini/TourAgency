package com.tour.service.impl;

import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.repository.GroupRepository;
import com.tour.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class GroupServiceImpl implements GroupService {


    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    public void deleteGroupById(long id) {
        groupRepository.delete(id);
    }

    public Group getGroupById(long id) {
        return groupRepository.findOne(id);
    }

    public List<Group> getGroupsByGuide(Guide guide) {
        return groupRepository.findByGuide(guide);
    }

    public List<Group> getGroupsByTourist(Tourist tourist) {
        return groupRepository.findByTourists(tourist);
    }

    public List<Group> getGroupsByTour(Tour tour) {
        return groupRepository.findByTour(tour);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }


    public void deleteAll() {
        groupRepository.deleteAll();
    }
}
