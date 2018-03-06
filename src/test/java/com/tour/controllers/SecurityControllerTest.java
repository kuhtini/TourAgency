package com.tour.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TimeZone;

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
//@Transactional

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

    @Autowired
    private GuideAccountService guideAccountService;

    @Autowired
    private GroupService groupService;

    private Tourist admin = setupAdmin(), tourist1 = nextTourist(), tourist2 = nextTourist();
    private Tour tour = nextTour();
    private Guide guide = nextGuide();


    @Before
    public void init() {

        touristAccountService.addNewUser(admin);
        touristAccountService.addNewUser(tourist1);
        touristAccountService.addNewUser(tourist2);
        guideAccountService.addNewUser(guide);
        tour.setStatus(Tour.TourStatus.ACTIVE);
        tourService.addNewTour(tour);


    }

    @After
    public void clean(){

        groupService.deleteAll();
        tourService.deleteAll();
        guideAccountService.deleteAll();
        touristAccountService.deleteAll();
    }

    @Test
    @Transactional
    public void addTourByPutJson() throws Exception {


        //TimeZone timeZone = TimeZone.getDefault();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //objectMapper.setDateFormat(dateFormat);
        //objectMapper.setTimeZone(timeZone);
        nextTour();
        Tour sendingTour = nextTour();


        sendingTour.setStatus(Tour.TourStatus.ACTIVE);
        String putBodyTour = objectMapper.writeValueAsString(sendingTour);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/tours")
                .accept(MediaTypes.HAL_JSON)
                .content(putBodyTour)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(admin.getUserName()))));

        sendingTour = tourService.getTourById(tourService.getAllTours().get(1).getId());

        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/tours/" + sendingTour.getId()))
                .andExpect(jsonPath("$.name", is(sendingTour.getName())))
                .andExpect(jsonPath("$.fromDate", is(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(sendingTour.getFromDate()))))
                .andExpect(jsonPath("$.byDate", is(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(sendingTour.getByDate()))))
                .andExpect(jsonPath("$.status", is(sendingTour.getStatus().name())))
                .andExpect(jsonPath("$.startCity", is(sendingTour.getStartCity())))
                .andExpect(jsonPath("$.cities", is(sendingTour.getCities())))
                .andExpect(jsonPath("$.price", is(sendingTour.getPrice())))
                .andExpect(jsonPath("$.description", is(sendingTour.getDescription())))
                .andExpect(jsonPath("$._links.join.href", is(linkTo(methodOn(TourResourceController.class).joinInGroup(sendingTour.getId(), null)).toString())));


    }

    //    @Test
//    public void currentUserNameGuide() throws Exception {
//    }
//
    @Test
    public void anonymousCantJoinInTheTour() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/1/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    public void anonymousDoesNotGetLink() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/1/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._links.join").doesNotExist());

    }

    @Test
    public void guideJoinLinkToGroup() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._links.join").doesNotExist());

    }

    @Test
    @Transactional
    public void guideOutLinkFromGroup() throws Exception {


//        Tour tour = nextTour();
//
//        tourService.addNewTour(tour);
//        tour = tourService.getTourById(tour.getId());
//        groupService.getGroupsByTour(tour);


        mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));


        Set<Group> groups = guide.getGroups();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));


        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._links.join").doesNotExist())
                .andExpect(jsonPath("$._links.leave.href", is(linkTo(methodOn(TourResourceController.class).getOut(tour.getId(), null)).toString())));

    }


}