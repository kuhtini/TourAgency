package com.tour.services.security;

import com.tour.model.Role;
import com.tour.model.interfaces.IUser;
import com.tour.repository.GuideRepository;
import com.tour.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private TouristRepository touristRepository;

    private GuideRepository guideRepository;

    @Autowired
    public UserDetailServiceImpl(TouristRepository touristRepository, GuideRepository guideRepository) {
        this.touristRepository = touristRepository;
        this.guideRepository = guideRepository;
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        IUser user = guideRepository.findByUserName(s);
        if (user == null) {
            user = touristRepository.findByUserName(s);
        }

        if (user == null) throw new UsernameNotFoundException(s + " not found");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }


}
