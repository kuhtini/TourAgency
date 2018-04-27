package com.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.tour.repository")
//@EntityScan("com.tour.model")
//@PropertySource({"dataSource.properties","application.properties"})
//@Import(RepositoryRestMvcConfiguration.class)
public class TourAgencyApplication {



    public static void main(String[] args) {

        SpringApplication.run(TourAgencyApplication.class, args);
    }


}
