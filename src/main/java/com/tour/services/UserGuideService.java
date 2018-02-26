package com.tour.services;

import com.tour.model.enums.UserRole;
import com.tour.model.Guide;
import com.tour.repository.GuideRepository;
import com.tour.services.intefaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserGuideService implements IUserService<Guide> {


    private GuideRepository guideRepository;


    @Autowired
    public UserGuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    public void addNewUser(Guide user) {

        guideRepository.save(user);
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
        return guideRepository.findByEmailLike(email);
    }

    public List<Guide> getUserLikeByUserName(String userName) {
        return guideRepository.findByUserNameLike(userName);
    }

    public Guide getUserById(long id) {
        return guideRepository.findOne(id);
    }

    public List<Guide> getUsersByLastName(String lastName) {
        return guideRepository.findByLastName(lastName);
    }
}
