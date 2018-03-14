package com.tour.services.impl;

import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.enums.UserRole;
import com.tour.repository.GuideRepository;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
//@Transactional
public class GuideAccountServiceImpl implements GuideAccountService {

    private GroupService groupService;
    private GuideRepository guideRepository;
    private TourService tourService;
    private PasswordEncoder encoder;


    @Autowired
    public GuideAccountServiceImpl(GroupService groupService, GuideRepository guideRepository, TourService tourService, PasswordEncoder encoder) {
        this.groupService = groupService;
        this.guideRepository = guideRepository;
        this.tourService = tourService;
        this.encoder = encoder;
    }


    public Guide saveUser(Guide user) {
        return guideRepository.save(user);

    }

    public void deleteUser(Guide user) {
        guideRepository.delete(user);
    }

    public Guide getUserByUserName(String userName) {

        return guideRepository.findByUserName(userName);
    }

    public Guide getUserByEmail(String email) {
        return guideRepository.findByEmail(email);
    }

    public List<Guide> getAllUsers() {
        return guideRepository.findAll();
    }

    public List<Guide> getAllUsersByUserRole(UserRole userRole) {
        return guideRepository.findByRoles(userRole);
    }

    public List<Guide> getUserLikeByEmail(String email) {
        return guideRepository.findByEmailLike("%" + email + "%");
    }

    public List<Guide> getUserLikeByUserName(String userName) {
        return guideRepository.findByUserNameLike("%" + userName + "%");
    }

    public Guide getUserById(Long id) {
        return guideRepository.findOne(id);
    }

    public List<Guide> getUsersByLastName(String lastName) {
        return guideRepository.findByLastName(lastName);
    }

    public void deleteUser(Long id) {
        guideRepository.delete(id);
    }

    public void deleteAll() {
        guideRepository.deleteAll();
    }

    public Guide addNewUser(Guide user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return guideRepository.save(user);
    }

    public void joinInGroup(long guideId, long groupId) {

        Group group = groupService.getGroupById(groupId);
        Guide guide = guideRepository.findOne(guideId);
        if (group.getGuide().equals(guide)) {

            //throw
        } else {
            group.setGuide(guide);
            guide.joinInToGroup(group);

            groupService.addGroup(group);
            guideRepository.save(guide);
        }


    }

    public void joinInGroupAsTourist(long guideId, long groupId) {
        Group group = groupService.getGroupById(groupId);
        Guide guide = guideRepository.findOne(guideId);
        if (group.getGuide().equals(guide)) {

            //throw
        } else {
            group.addTourist(guide);
            guide.joinInToGroupAsTourist(group);

            groupService.addGroup(group);
            guideRepository.save(guide);
        }
    }

    public boolean isInGroup(long guideID, long tourId) {

        List<Group> tourGroup = tourService.getTourById(tourId).getGroups();

        Set<Group> guideGroups = guideRepository.findOne(guideID).getGroups();

        return !Collections.disjoint(tourGroup, guideGroups);

    }

    public boolean isInGroupAsTourist(long guideID, long tourId) {

        List<Group> tourGroup = tourService.getTourById(tourId).getGroups();

        Set<Group> guideGroups = guideRepository.findOne(guideID).getGroupsLikeTourist();

        return !Collections.disjoint(tourGroup, guideGroups);

    }
}


