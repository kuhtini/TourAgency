package com.tour.services;

import com.tour.model.enums.UserRole;
import com.tour.model.Tourist;
import com.tour.repository.TouristRepository;
import com.tour.services.intefaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserTouristService implements IUserService<Tourist> {


    private TouristRepository touristRepository;

    @Autowired
    public UserTouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;

    }

    public void addNewUser(Tourist user) {
        touristRepository.save(user);
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
        return touristRepository.findByEmailLike(email);
    }

    public List<Tourist> getUserLikeByUserName(String userName) {
        return touristRepository.findByUserNameLike(userName);
    }

    public Tourist getUserById(long id) {
        return touristRepository.findOne(id);
    }

    public List<Tourist> getUsersByLastName(String lastName) {
        return touristRepository.findByLastName(lastName);
    }
}
