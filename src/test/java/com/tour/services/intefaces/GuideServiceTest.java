package com.tour.services.intefaces;

import com.tour.model.Guide;
import com.tour.model.Tourist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.ConditionalOnInitializedRestarter;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.tour.services")
public class GuideServiceTest {

    @Autowired
    private GuideService guideService;

    private Guide guide, guide1, guide2;


    @Before
   // @Rollback(value = false)
    public void initialEntities() {
        guide = new Guide();
        guide.setFirstName("Park");
        guide.setLastName("Ambham");
        guide.setUserName("Park991");
        guide.setEmail("lordoftherings2000@mail.com");


        guide1 = new Guide();
        guide1.setFirstName("Roman");
        guide1.setLastName("Wilals");
        guide1.setUserName("ARHANGEL991");
        guide1.setEmail("lordofthemachinesT800@mail.com");

        guide2 = new Guide();
        guide2.setFirstName("Peter");
        guide2.setLastName("Jackson");
        guide2.setUserName("JacksonOn");
        guide2.setEmail("waaagrrOrks@mail.com");

        guideService.addUser(guide);
        guideService.addUser(guide1);
        guideService.addUser(guide2);
    }
//
//    @After
//    public void clearDB() {
//        guideService.deleteAll();
//    }


    @Test
    public void addUser() throws Exception {


        assertEquals(guideService.getUserById(guide.getId()), guide);
    }

    @Test
    public void deleteUserById() throws Exception {

        guideService.deleteUser(guide);
        assertNull(guideService.getUserById(guide.getId()));
    }

    @Test
    public void getAllUsersWhenEmpty() throws Exception {

        guideService.deleteAll();
        assertEquals(guideService.getAllUsers(), new ArrayList<Guide>());
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteWhenNotExist() {

        guideService.deleteUser((long) 2);

    }


    @Test
    public void getUserByUserName() throws Exception {


        //guideService.addUser(guide);

        assertEquals(guide, guideService.getUserByUserName(guide.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {


        //guideService.addUser(guide);

        assertEquals(guide, guideService.getUserByEmail(guide.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1)), guideService.getUserLikeByEmail("lord"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1)), guideService.getUserLikeByUserName("991"));

    }

    @Test
    public void getUserById() throws Exception {


        assertEquals(guide, guideService.getUserById(guide.getId()));

    }

    @Test
    public void getUsersByLastName() throws Exception {

        assertEquals(guideService.getUsersByLastName(guide.getLastName()), Arrays.asList(guide));
    }
}