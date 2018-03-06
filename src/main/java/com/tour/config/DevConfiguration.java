package com.tour.config;

import com.tour.model.*;
import com.tour.model.enums.UserRole;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import com.tour.services.impl.TouristAccountServiceImpl;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static com.tour.utils.Creator.*;

@Configuration
@Profile("DEV")
public class DevConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final boolean FIRST_LAUNCH = true;




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner cmd(TouristAccountService touristService, GuideAccountService guideService, TourService tourService, GroupService groupService) {
        return (args) -> {

            if (FIRST_LAUNCH) {
                int amount = 20;


                Tourist tourist;
                tourist = setupAdmin();
                touristService.addNewUser(tourist);

                log.info("---------------Default ADMIN Created---------------------");


                log.info("-----------------------------------------");
                for (int i = 0; i < amount; i++) {

                    log.info("--------------------Create entity " + i + " out of " + amount + " --------------------");
                    tourist = nextTourist();
                    Tour tour = nextTour();
                    Group group = nextGroup(tour);

                    touristService.addNewUser(tourist);

                    tourService.addNewTour(tour);

                   // groupService.joinInToGroup(group);
                }

                //Admin Setup



                for (int i = 0; i < amount/4; i++) {
                    log.info("--------------------Create guide " + i + " out of " + amount + " --------------------");
                    Guide guide = nextGuide();
                    guideService.addNewUser(guide);
                }

            }
            log.info("--------------------Create entities DONE --------------------");
            log.info("-------------------------------------------------------------");

        };
    }

}
