package com.tour;

import com.tour.model.Tourist;
import com.tour.services.TouristServiceImpl;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("DEV")
public class DevConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Application.class);



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner cmd(TouristServiceImpl touristRepository) {
        return (args) -> {
            // save a couple of customers

            // fetch all customers
            log.info("Tourist found with findAll():");
            log.info("-------------------------------");
            for (Tourist tourist : touristRepository.getAllUsers()) {
                log.info(tourist.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Tourist tourist = touristRepository.getUserById(1L);
            log.info("Tourist found with findOne(1L):");
            log.info("--------------------------------");
            log.info(tourist.toString());
            log.info("");

            // fetch customers by last name
            log.info("Tourist found with findByLastName('Example'):");
            log.info("--------------------------------------------");
            for (Tourist bauer : touristRepository.getUsersByLastName("Example")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }
}
