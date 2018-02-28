package com.tour.services;

import com.tour.model.Guide;
import com.tour.repository.GuideRepository;
import com.tour.services.intefaces.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GuideServiceImpl implements GuideService {


    private GuideRepository guideRepository;

    @Autowired
    public GuideServiceImpl(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    public void addUser(Guide user) {
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
}
