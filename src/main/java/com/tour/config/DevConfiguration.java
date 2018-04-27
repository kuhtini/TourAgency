package com.tour.config;

import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.service.GroupService;
import com.tour.service.GuideAccountService;
import com.tour.service.TourService;
import com.tour.service.TouristAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.tour.utils.Creator.*;

@Configuration
@Profile("DEV")
@Slf4j
public class DevConfiguration {

    private static final boolean FIRST_LAUNCH = true;





    @Bean
    public CommandLineRunner cmd(TouristAccountService touristService, GuideAccountService guideService, TourService tourService, GroupService groupService) {
        return (args) -> {


            //create start entities
            if (FIRST_LAUNCH) {
                int amount = 20;


                Tourist tourist;
                tourist = setupAdmin();
                touristService.addNewUser(tourist);

                log.info("---------------Default ADMIN Created---------------------");


                log.info("-----------------------------------------");
                for (int i = 0; i < amount; i++) {

                    log.debug("--------------------Create entity {} out of {} --------------------", i, amount);
                    tourist = nextTourist();
                    Tour tour = nextTour();
                    log.debug("--------------------Create entity DONE{} out of {}--------------------", i, amount);
                    log.debug("--------------------Commit entity {} out of {} --------------------", i, amount);
                    touristService.addNewUser(tourist);

                    tourService.addNewTour(tour);
                    log.debug("--------------------Commit entity DONE {} out of {}--------------------", i, amount);
                }

                //Admin Setup


                for (int i = 0; i < amount / 4; i++) {
                    log.debug("--------------------Create guide {} out of {} --------------------", i, amount);
                    Guide guide = nextGuide();
                    guideService.addNewUser(guide);
                }

            }
            log.info("--------------------Create entities DONE --------------------");
            log.info("-------------------------------------------------------------");

        };
    }

}
