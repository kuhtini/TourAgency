package com.tour.services;

import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.repository.GroupRepository;
import com.tour.services.intefaces.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GroupService implements IGroupService {


   private GroupRepository groupRepository;

   @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void addGroup(Group group) {
         groupRepository.save(group);
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
}
