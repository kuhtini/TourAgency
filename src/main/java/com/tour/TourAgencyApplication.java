package com.tour;

import com.tour.model.Tourist;
import com.tour.repository.TouristRepository;
import com.tour.services.UserTouristService;
import com.tour.services.intefaces.UserService;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.tour.repository")
@EntityScan("com.tour.model")
@PropertySource("dataSource.properties")
@Import(RepositoryRestMvcConfiguration.class)
@PropertySource("application.properties")
public class TourAgencyApplication {


	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		SpringApplication.run(TourAgencyApplication.class, args);
	}


}
