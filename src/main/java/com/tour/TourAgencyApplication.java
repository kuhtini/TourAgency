package com.tour;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.tour.repository")
//@EntityScan("com.tour.model")
//@PropertySource({"dataSource.properties","application.properties"})
//@Import(RepositoryRestMvcConfiguration.class)
public class TourAgencyApplication {


    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        SpringApplication.run(TourAgencyApplication.class, args);
    }


}
