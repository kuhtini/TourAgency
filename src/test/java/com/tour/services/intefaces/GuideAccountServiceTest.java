package com.tour.services.intefaces;

import com.tour.config.H2Config;
import com.tour.config.SecurityConfiguration;
import com.tour.model.Guide;
import com.tour.services.GuideAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;


import static com.tour.utils.Creator.nextGuide;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class GuideAccountServiceTest {

    @Autowired
    private GuideAccountService guideAccountService;

    private Guide guide = new Guide(), guide1 = new Guide(), guide2 = new Guide();


    @Before
    public void initialEntities() {
//        guide.setFirstName("Park");
//        guide.setLastName("Ambham");
//        guide.setUserName("Park991");
//        guide.setEmail("lordoftherings2000@mail.com");
//
//
//        guide1.setFirstName("Roman");
//        guide1.setLastName("Wilals");
//        guide1.setUserName("ARHANGEL991");
//        guide1.setEmail("lordofthemachinesT800@mail.com");
//
//        guide2.setFirstName("Peter");
//        guide2.setLastName("Jackson");
//        guide2.setUserName("JacksonOn");
//        guide2.setEmail("waaagrrOrks@mail.com");

        guide = nextGuide();
        guide1 = nextGuide();
        guide2 = nextGuide();


        guideAccountService.saveUser(guide);
        guideAccountService.saveUser(guide1);
        guideAccountService.saveUser(guide2);
    }

//    @After
//    public void clearDB() {
//        guideAccountService.deleteAll();
//    }


    @Test
    public void addUser() throws Exception {


        assertEquals(guideAccountService.getUserById(guide.getId()), guide);
    }

    @Test
    public void deleteUserById() throws Exception {

        guideAccountService.deleteUser(guide);
        assertNull(guideAccountService.getUserById(guide.getId()));
    }

    @Test
    public void getAllUsersWhenEmpty() throws Exception {

        guideAccountService.deleteAll();
        assertEquals(guideAccountService.getAllUsers(), new ArrayList<Guide>());
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteWhenNotExist() {

        guideAccountService.deleteAll();
        guideAccountService.deleteUser((long) 2);

    }


    @Test
    public void getUserByUserName() throws Exception {


        //guideAccountService.saveUser(guide);

        assertEquals(guide, guideAccountService.getUserByUserName(guide.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {


        //guideAccountService.saveUser(guide);

        assertEquals(guide, guideAccountService.getUserByEmail(guide.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1,guide2)), guideAccountService.getUserLikeByEmail("@gmail.com"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1,guide2)), guideAccountService.getUserLikeByUserName("r"));

    }

    @Test
    public void getUserById() throws Exception {


        assertEquals(guide, guideAccountService.getUserById(guide.getId()));

    }

    @Test
    public void getUsersByLastName() throws Exception {

        assertEquals(guideAccountService.getUsersByLastName(guide.getLastName()), Arrays.asList(guide));
    }
}