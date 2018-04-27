package com.tour.service.impl;

import com.tour.model.Group;
import com.tour.model.Tourist;
import com.tour.model.enums.UserRole;
import com.tour.repository.TouristRepository;
import com.tour.service.GroupService;
import com.tour.service.TourService;
import com.tour.service.TouristAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
//@Transactional
public class TouristAccountServiceImpl implements TouristAccountService {


    private TouristRepository touristRepository;
    private GroupService groupService;
    private TourService tourService;
    private PasswordEncoder encoder;

    @Autowired
    public TouristAccountServiceImpl(TouristRepository touristRepository, GroupService groupService, TourService tourService, PasswordEncoder encoder) {
        this.touristRepository = touristRepository;
        this.groupService = groupService;
        this.tourService = tourService;
        this.encoder = encoder;
    }

    public Tourist saveUser(Tourist user) {
        return touristRepository.save(user);
    }

    public Tourist addNewUser(Tourist user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return touristRepository.save(user);
    }

    public void deleteUser(Tourist user) {
        touristRepository.delete(user);
    }

    public List<Tourist> getAllUsers() {
        return touristRepository.findAll();
    }

    public List<Tourist> getAllUsersByUserRole(UserRole userRole) {
        return touristRepository.findByRoles(userRole);
    }

    public Tourist getUserByUserName(String userName) {
        return touristRepository.findByUserName(userName);
    }

    public Tourist getUserByEmail(String email) {
        return touristRepository.findByEmail(email);
    }

    public List<Tourist> getUserLikeByEmail(String email) {
        return touristRepository.findByEmailLike("%" + email + "%");
    }

    public List<Tourist> getUserLikeByUserName(String userName) {
        return touristRepository.findByUserNameLike("%" + userName + "%");
    }

    public Tourist getUserById(Long id) {
        return touristRepository.findOne(id);
    }

    public List<Tourist> getUsersByLastName(String lastName) {
        return touristRepository.findByLastName(lastName);
    }

    public void deleteUser(Long id) {
        touristRepository.delete(id);
    }

    public void deleteAll() {
        touristRepository.deleteAll();
    }


    public void joinInGroup(long touristId, long groupId) {

        Group group = groupService.getGroupById(groupId);
        Tourist tourist = touristRepository.findOne(touristId);
        if (tourist.getGroups().contains(group)) {
            //TODO
            //throw
        } else {
            tourist.joinInToGroup(group);
            group.addTourist(tourist);
            groupService.addGroup(group);

        }
    }

    public boolean isInGroup(long touristID, long tourId) {

        List<Group> tourGroup = tourService.getTourById(tourId).getGroups();

        List<Group> guideGroups = touristRepository.findOne(touristID).getGroups();

        return !Collections.disjoint(tourGroup, guideGroups);

    }
}
