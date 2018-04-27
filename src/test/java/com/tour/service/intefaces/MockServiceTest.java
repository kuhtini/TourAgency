package com.tour.service.intefaces;


import com.tour.model.Guide;
import com.tour.model.Tourist;
import com.tour.service.GuideAccountService;
import com.tour.service.TouristAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;

import static com.tour.utils.Creator.nextGuide;
import static com.tour.utils.Creator.nextTourist;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockServiceTest {

    @MockBean
    private TouristAccountService touristAccountService;

    @MockBean
    private GuideAccountService guideAccountService;

    @MockBean
    private UserDetailsService userDetailsService;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void meRequestTest() throws Exception {

        Tourist tourist = nextTourist();
        Guide guide = nextGuide();


        when(touristAccountService.getUserByUserName(any())).thenReturn(tourist);
        when(guideAccountService.getUserByUserName(any())).thenReturn(guide);


        when(userDetailsService.loadUserByUsername(guide.getUserName()))
                .thenReturn(new org.springframework.security.core.userdetails.User
                        (tourist.getUserName(),
                                tourist.getPassword(),
                                guide.getRoles().stream().map(
                                        role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet())
                        )
                );


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/guides/me")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsService.loadUserByUsername(guide.getUserName()))));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is(guide.getUserName())));


    }
}
