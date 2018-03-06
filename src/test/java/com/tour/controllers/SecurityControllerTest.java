package com.tour.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;

import static com.tour.utils.Creator.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ComponentScan({"com.tour"})
@AutoConfigureMockMvc
@Transactional
public class SecurityControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private SecurityController securityController;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TouristAccountService touristAccountService;

    @Autowired
    private TourService tourService;

    Tourist admin = setupAdmin(), tourist1 = nextTourist(), tourist2 = nextTourist();
    Guide guide = nextGuide();



    @Before
    public void init() {

        touristAccountService.addNewUser(admin);
        touristAccountService.addNewUser(tourist1);
        touristAccountService.addNewUser(tourist2);


    }

    @Test
    public void currentUserNameTourist() throws Exception {

        Tour tour = nextTour();

        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm"));

        String putBodyTour = objectMapper.writeValueAsString(tour);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/tours")
                .accept(MediaTypes.HAL_JSON)
                .content(putBodyTour)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(admin.getUserName()))));

        tour = tourService.getAllTours().get(0);

        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/tours/" + tour.getId()))
                .andExpect(jsonPath("$.name", is(tour.getName())))
                .andExpect(jsonPath("$.fromDate", is(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(tour.getFromDate()))))
                .andExpect(jsonPath("$.byDate", is(new SimpleDateFormat("dd-MM-yyyy  HH:mm").format(tour.getByDate()))))
                .andExpect(jsonPath("$.status", is(tour.getStatus().name())))
                .andExpect(jsonPath("$.startCity", is(tour.getStartCity())))
                .andExpect(jsonPath("$.cities", is(tour.getCities().toString())))
                .andExpect(jsonPath("$.price", is(tour.getPrice())))
                .andExpect(jsonPath("$.description", is(tour.getDescription())))
                .andExpect(jsonPath("$_links.join.href", is(linkTo(methodOn(TourResourceController.class).joinInGroup(tour.getId(), null)).toUri().toString())));


    }

    @Test
    public void currentUserNameGuide() throws Exception {
    }

}