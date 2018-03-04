package com.tour.services.intefaces;

import com.tour.model.Tourist;
import com.tour.services.TouristAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.tour.services")
public class TouristAccountServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TouristAccountService touristAccountService;


     private Tourist tourist = new Tourist(),tourist1= new Tourist(),tourist2= new Tourist();

    @Before
    public void setUp() throws Exception {

        tourist.setFirstName("Park");
        tourist.setLastName("Ambham");
        tourist.setUserName("PARKisONair991");
        tourist.setEmail("lordoftherings2000@mail.com");


        tourist1.setFirstName("Roman");
        tourist1.setLastName("Wilals");
        tourist1.setUserName("ARHANGEL991");
        tourist1.setEmail("lordofthemachinesT800@mail.com");

        tourist2.setFirstName("Peter");
        tourist2.setLastName("Jackson");
        tourist2.setUserName("JacksonOn");
        tourist2.setEmail("waaagrrOrks@mail.com");

        touristAccountService.saveUser(tourist);
        touristAccountService.saveUser(tourist1);
        touristAccountService.saveUser(tourist2);

    }

    @Test
    public void addUser() throws Exception {

        assertEquals(touristAccountService.getUserById(tourist.getId()), tourist);
    }

    @Test
    public void deleteUserById() throws Exception {


        touristAccountService.deleteAll();
        assertEquals(touristAccountService.getAllUsers(), new ArrayList<Tourist>());
    }

    @Test
    public void getAllUsersWhenEmpty() throws Exception {
        touristAccountService.deleteAll();
        assertEquals(touristAccountService.getAllUsers(), new ArrayList<Tourist>());
   }


    @Test(expected = EmptyResultDataAccessException.class )
    public void deleteWhenNotExist() {

        touristAccountService.deleteUser((long) 2);

    }


    @Test
    public void getUserByUserName() throws Exception {


        assertEquals(tourist, touristAccountService.getUserByUserName(tourist.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {



        assertEquals(tourist, touristAccountService.getUserByEmail(tourist.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(tourist,tourist1)), touristAccountService.getUserLikeByEmail("lord"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(tourist,tourist1)), touristAccountService.getUserLikeByUserName("991"));

    }

    @Test
    public void getUserById() throws Exception {


        assertEquals(tourist,touristAccountService.getUserById(tourist.getId()));
    }

    @Test
    public void getUsersByLastName() throws Exception {



        assertEquals(touristAccountService.getUsersByLastName(tourist2.getLastName()),Arrays.asList(tourist2));
    }

}