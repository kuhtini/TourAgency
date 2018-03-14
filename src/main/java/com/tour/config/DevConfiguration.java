package com.tour.config;

import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;

import static com.tour.utils.Creator.*;

@Configuration
@Profile("DEV")
public class DevConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final boolean FIRST_LAUNCH = true;





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
                    log.debug("--------------------Create entity DONE" + i + " out of " + amount + "--------------------");
                    log.debug("--------------------Commit entity " + i + " out of " + amount + " --------------------");
                    touristService.addNewUser(tourist);

                    tourService.addNewTour(tour);
                    log.debug("--------------------Commit entity DONE " + i + " out of " + amount + "--------------------");
                    // groupService.joinInToGroup(group);
                }

                //Admin Setup


                for (int i = 0; i < amount / 4; i++) {
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
