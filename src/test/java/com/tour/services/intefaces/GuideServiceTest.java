package com.tour.services.intefaces;

import com.tour.model.Guide;
import com.tour.model.Tourist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.tour.services")
public class GuideServiceTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private GuideService guideService;


    @Test
    public void addUser() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Peter");
        guide.setLastName("Jackson");

        guideService.addUser(guide);


        assertEquals(guideService.getUserById(guide.getId()), guide);
    }

    @Test
    public void deleteUserById() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Peter");
        guide.setLastName("Jackson");

        guideService.addUser(guide);

        guideService.deleteAll();
        assertEquals(guideService.getAllUsers(), new ArrayList<Guide>());
    }

    @Test
    public void getAllUsersWhenEmpty() throws Exception {
        assertEquals(guideService.getAllUsers(), new ArrayList<Guide>());
    }


    @Test(expected = EmptyResultDataAccessException.class )
    public void deleteWhenNotExist() {

        guideService.deleteUser((long) 2);

    }


    @Test
    public void getUserByUserName() throws Exception {
        Guide guide = new Guide();
        guide.setFirstName("Peter");
        guide.setLastName("Jackson");
//        tourist.setRoles(new HashSet<>(Arrays.asList(new Role("ROLE_ADMIN"),new Role("ROLE_USER"))));
//
//        Tourist tourist1 = new Tourist();
//        tourist.setFirstName("Park");
//        tourist.setLastName("Ambham");
//        tourist.setRoles(new HashSet<>(Arrays.asList(new Role("ROLE_USER"))));
//
//        Tourist tourist2 = new Tourist();
//        tourist.setFirstName("Roman");
//        tourist.setLastName("Wilals");
//        tourist.setRoles(new HashSet<>(Arrays.asList(new Role("ROLE_STAFF"))));

        guideService.addUser(guide);

        assertEquals(guide, guideService.getUserByUserName(guide.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Peter");
        guide.setLastName("Jackson");
        guide.setEmail("lordoftherings2000@mail.com");

        guideService.addUser(guide);

        assertEquals(guide, guideService.getUserByEmail(guide.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {
        Guide guide = new Guide();
        guide.setFirstName("Park");
        guide.setLastName("Ambham");
        guide.setEmail("lordoftherings2000@mail.com");


        Guide guide1 = new Guide();
        guide1.setFirstName("Roman");
        guide1.setLastName("Wilals");
        guide1.setEmail("lordofthemachinesT800@mail.com");

        Guide guide3 = new Guide();
        guide3.setFirstName("Peter");
        guide3.setLastName("Jackson");
        guide3.setEmail("waaagrrOrks@mail.com");

        guideService.addUser(guide);
        guideService.addUser(guide1);
        guideService.addUser(guide3);

        assertEquals(new ArrayList<>(Arrays.asList(guide,guide1)), guideService.getUserLikeByEmail("lord"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Park");
        guide.setLastName("Ambham");
        guide.setUserName("Park991");
        guide.setEmail("lordoftherings2000@mail.com");


        Guide guide1 = new Guide();
        guide1.setFirstName("Roman");
        guide1.setLastName("Wilals");
        guide1.setUserName("ARHANGEL991");
        guide1.setEmail("lordofthemachinesT800@mail.com");

        Guide guide2 = new Guide();
        guide2.setFirstName("Peter");
        guide2.setLastName("Jackson");
        guide2.setUserName("JacksonOn");
        guide2.setEmail("waaagrrOrks@mail.com");

        guideService.addUser(guide);
        guideService.addUser(guide1);
        guideService.addUser(guide2);

        assertEquals(new ArrayList<>(Arrays.asList(guide,guide1)), guideService.getUserLikeByUserName("991"));

    }

    @Test
    public void getUserById() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Park");
        guide.setLastName("Ambham");
        guide.setUserName("Park991");
        guide.setEmail("lordoftherings2000@mail.com");


        Guide guide2 = new Guide();
        guide2.setFirstName("Roman");
        guide2.setLastName("Wilals");
        guide2.setUserName("ARHANGEL991");
        guide2.setEmail("lordofthemachinesT800@mail.com");

        guideService.addUser(guide);
        guideService.addUser(guide2);

    }

    @Test
    public void getUsersByLastName() throws Exception {

        Guide guide = new Guide();
        guide.setFirstName("Roman");
        guide.setLastName("Wilals");
        guide.setUserName("ARHANGEL991");
        guide.setEmail("lordofthemachinesT800@mail.com");

        guideService.addUser(guide);

        assertEquals(guideService.getUsersByLastName(guide.getLastName()),Arrays.asList(guide));
    }
}