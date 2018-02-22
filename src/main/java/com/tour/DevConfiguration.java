package com.tour;

import com.tour.model.Tourist;
import com.tour.services.UserTouristService;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("DEV")
public class DevConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public CommandLineRunner cmd(UserTouristService touristRepository) {
        return (args) -> {
            // save a couple of customers

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Tourist tourist : touristRepository.getAllUsers()) {
                log.info(tourist.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Tourist tourist = touristRepository.getUserById(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(tourist.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('Example'):");
            log.info("--------------------------------------------");
            for (Tourist bauer : touristRepository.getUsersByLastName("Example")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }
}
