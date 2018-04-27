package com.tour.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.repository.BaseUserRepository;
import com.tour.service.GroupService;
import com.tour.service.GuideAccountService;
import com.tour.service.TourService;
import com.tour.service.TouristAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

import static com.tour.utils.Creator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
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

    @Autowired
    private BaseUserRepository userRepository;

    private Tourist admin = setupAdmin(), tourist1 = nextTourist(), tourist2 = nextTourist();
    private Tour tour = nextTour();
    private Guide guide = nextGuide();
    private Tour sendingTour = nextTour();


    private RestTemplate restTemplate;


    @Before
    public void init() {

        touristAccountService.addNewUser(admin);
        touristAccountService.addNewUser(tourist1);
        touristAccountService.addNewUser(tourist2);
        guideAccountService.addNewUser(guide);
        tour.setStatus(Tour.TourStatus.ACTIVE);
        tourService.addNewTour(tour);

        MockitoAnnotations.initMocks(this);

    }

    @After
    public void clean() {

        groupService.deleteAll();
        guideAccountService.deleteAll();
        tourService.deleteAll();
        touristAccountService.deleteAll();
    }

    @Test
    public void addTourByPutJson() throws Exception {   //TODO fix Null userType


        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));

        sendingTour.setStatus(Tour.TourStatus.ACTIVE);
        String putBodyTour = objectMapper.writeValueAsString(sendingTour);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/tours")
                .accept(MediaTypes.HAL_JSON)
                .content(putBodyTour)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))))
                .andDo(print());

        sendingTour = tourService.getTourById(tourService.getAllTours().get(1).getId());


        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/tours/" + sendingTour.getId()))
                .andExpect(jsonPath("$.name", is(sendingTour.getName())))
                .andExpect(jsonPath("$.fromDate", is(new SimpleDateFormat("dd-MM-yyyy HH:mm")
                        .format(sendingTour.getFromDate()))))
                .andExpect(jsonPath("$.byDate", is(new SimpleDateFormat("dd-MM-yyyy HH:mm")
                        .format(sendingTour.getByDate()))))
                .andExpect(jsonPath("$.status", is(sendingTour.getStatus().name())))
                .andExpect(jsonPath("$.startCity", is(sendingTour.getStartCity())))
                .andExpect(jsonPath("$.price", is(sendingTour.getPrice())))
                .andExpect(jsonPath("$.description", is(sendingTour.getDescription())))
                .andExpect(jsonPath("$._links.join.href", is(linkTo(methodOn(TourResourceController.class)
                        .joinInGroup(sendingTour.getId(), null)).toString())))
                .andDo(print());


        Tour tourFromServer = restTemplate.getForObject("http://localhost/tours/" + sendingTour.getId(), Tour.class);

        Group expectedGroup = sendingTour.getGroups().get(0);
        Group actualGroup = tourService.getTourById(sendingTour.getId()).getGroups().get(0);

        assertThat(expectedGroup).isEqualTo(actualGroup);
        //assertTrue(sendingTour.equals(tourFromServer));
        //assertThat(sendingTour).isEqualTo(tourFromServer);


    }

    @Test
    public void linksForGuideInTour() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName())))).andDo(print());

        resultActions
                .andExpect(jsonPath("$._links.join.href", is(linkTo(methodOn(TourResourceController.class).joinInGroup(tour.getId(), null)).toString())))
                .andExpect(jsonPath("$._links.joinAsTourist.href", is(linkTo(methodOn(TourResourceController.class).joinInGroupLikeTourist(tour.getId(), null)).toString())));


    }


    @Test
    public void anonymousCantJoinInTheTour() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    public void anonymousDoesNotGetLink() throws Exception {


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$._links.join").doesNotExist());

    }

    @Test
    public void touristExceptionIfLeaveFromTourThatDoesNotHaveThisTouristInGroups() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(tourist1.getUserName()))));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(tourist1.getUserName()))));

        resultActions.andExpect(status().isConflict());

    }

    @Test
    public void guideExceptionIfLeaveFromTourThatDoesNotHaveThisGuideInGroupsLikeGuide() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/join")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));

        resultActions.andExpect(status().isConflict());

    }

    @Test
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


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));


        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._links.join").doesNotExist())
                .andExpect(jsonPath("$._links.leave.href", is(linkTo(methodOn(TourResourceController.class).getOut(tour.getId(), null)).toString())));

    }

    @Test
    public void guideOutLinkFromGroupAsTourist() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .put("/tours/" + tour.getId() + "/joinAsTourist")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tours/" + tour.getId())
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));


        resultActions.andExpect(status().is2xxSuccessful())

                .andExpect(jsonPath("$._links.leave").doesNotExist())
                .andExpect(jsonPath("$._links.joinAsTourist").doesNotExist())
                .andExpect(jsonPath("$._links.leaveAsTourist.href", is(linkTo(methodOn(TourResourceController.class).leaveGroupAsTourist(tour.getId(), null)).toString())))
                .andExpect(jsonPath("$._links.join.href", is(linkTo(methodOn(TourResourceController.class).joinInGroup(tour.getId(), null)).toString())));


    }

    @Test
    public void anonymousCantGetSecureTourist() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tourists/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void anonymousCantGetSecureGuides() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/guides/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void touristCantGetGuides() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/guides/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(tourist1.getUserName()))));

        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void touristCantGetTourists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tourists/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(tourist1.getUserName()))));

        resultActions.andExpect(status().isForbidden());
    }


    @Test
    public void touristCanGetOnlySelf() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tourists/me")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(tourist1.getUserName()))));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is(tourist1.getUserName())));
    }

    @Test
    public void guideCanGetOnlySelf() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/guides/me")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is(guide.getUserName())));
    }

    @Test
    public void guideCanGetAllTourist() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/tourists/")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tourists").exists());
    }


}