package com.tour.services.intefaces;

import com.tour.model.Tourist;
import com.tour.services.intefaces.TouristService;
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
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.tour.services")
public class TouristServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TouristService touristService;


    @Test
    public void addUser() throws Exception {

        Tourist tourist = new Tourist();
        tourist.setFirstName("Peter");
        tourist.setLastName("Jackson");

        touristService.addUser(tourist);


        assertEquals(touristService.getUserById(tourist.getId()), tourist);
    }

    @Test
    public void deleteUserById() throws Exception {

        Tourist tourist = new Tourist();
        tourist.setFirstName("Peter");
        tourist.setLastName("Jackson");

        touristService.addUser(tourist);

        touristService.deleteAll();
        assertEquals(touristService.getAllUsers(), new ArrayList<Tourist>());
    }

    @Test
    public void getAllUsersWhenEmpty() throws Exception {
        assertEquals(touristService.getAllUsers(), new ArrayList<Tourist>());
   }


    @Test(expected = EmptyResultDataAccessException.class )
    public void deleteWhenNotExist() {

        touristService.deleteUser((long) 2);

    }


    @Test
    public void getUserByUserName() throws Exception {
        Tourist tourist = new Tourist();
        tourist.setFirstName("Peter");
        tourist.setLastName("Jackson");
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

        touristService.addUser(tourist);

        assertEquals(tourist,touristService.getUserByUserName(tourist.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {

        Tourist tourist = new Tourist();
        tourist.setFirstName("Peter");
        tourist.setLastName("Jackson");
        tourist.setEmail("lordoftherings2000@mail.com");

        touristService.addUser(tourist);

        assertEquals(tourist,touristService.getUserByEmail(tourist.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {
        Tourist tourist1 = new Tourist();
        tourist1.setFirstName("Park");
        tourist1.setLastName("Ambham");
        tourist1.setEmail("lordoftherings2000@mail.com");


        Tourist tourist2 = new Tourist();
        tourist2.setFirstName("Roman");
        tourist2.setLastName("Wilals");
        tourist2.setEmail("lordofthemachinesT800@mail.com");

        Tourist tourist3 = new Tourist();
        tourist3.setFirstName("Peter");
        tourist3.setLastName("Jackson");
        tourist3.setEmail("waaagrrOrks@mail.com");

        touristService.addUser(tourist1);
        touristService.addUser(tourist2);
        touristService.addUser(tourist3);

        assertEquals(new ArrayList<>(Arrays.asList(tourist1,tourist2)),touristService.getUserLikeByEmail("lord"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {
        Tourist tourist1 = new Tourist();
        tourist1.setFirstName("Park");
        tourist1.setLastName("Ambham");
        tourist1.setUserName("Park991");
        tourist1.setEmail("lordoftherings2000@mail.com");


        Tourist tourist2 = new Tourist();
        tourist2.setFirstName("Roman");
        tourist2.setLastName("Wilals");
        tourist2.setUserName("ARHANGEL991");
        tourist2.setEmail("lordofthemachinesT800@mail.com");

        Tourist tourist3 = new Tourist();
        tourist3.setFirstName("Peter");
        tourist3.setLastName("Jackson");
        tourist3.setUserName("JacksonOn");
        tourist3.setEmail("waaagrrOrks@mail.com");

        touristService.addUser(tourist1);
        touristService.addUser(tourist2);
        touristService.addUser(tourist3);

        assertEquals(new ArrayList<>(Arrays.asList(tourist1,tourist2)),touristService.getUserLikeByUserName("991"));

    }

    @Test
    public void getUserById() throws Exception {

        Tourist tourist1 = new Tourist();
        tourist1.setFirstName("Park");
        tourist1.setLastName("Ambham");
        tourist1.setUserName("Park991");
        tourist1.setEmail("lordoftherings2000@mail.com");


        Tourist tourist2 = new Tourist();
        tourist2.setFirstName("Roman");
        tourist2.setLastName("Wilals");
        tourist2.setUserName("ARHANGEL991");
        tourist2.setEmail("lordofthemachinesT800@mail.com");

        touristService.addUser(tourist1);
        touristService.addUser(tourist2);

    }

    @Test
    public void getUsersByLastName() throws Exception {

        Tourist tourist2 = new Tourist();
        tourist2.setFirstName("Roman");
        tourist2.setLastName("Wilals");
        tourist2.setUserName("ARHANGEL991");
        tourist2.setEmail("lordofthemachinesT800@mail.com");

        touristService.addUser(tourist2);

        assertEquals(touristService.getUsersByLastName(tourist2.getLastName()),Arrays.asList(tourist2));
    }

}