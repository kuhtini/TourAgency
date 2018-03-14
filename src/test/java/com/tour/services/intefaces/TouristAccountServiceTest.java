package com.tour.services.intefaces;

import com.tour.model.Tourist;
import com.tour.services.TouristAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tour.utils.Creator.nextTourist;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TouristAccountServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TouristAccountService touristAccountService;


    private Tourist tourist = new Tourist(), tourist1 = new Tourist(), tourist2 = new Tourist();

    @Before
    public void setUp() throws Exception {

        tourist = nextTourist();

        tourist1 = nextTourist();

        tourist2 = nextTourist();

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


    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteWhenNotExist() {

        touristAccountService.deleteAll();
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


        assertEquals(new ArrayList<>(Arrays.asList(tourist, tourist1, tourist2)), touristAccountService.getUserLikeByEmail("@gmail.com"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(tourist, tourist1, tourist2)), touristAccountService.getUserLikeByUserName("r"));

    }

    @Test
    public void getUserById() throws Exception {


        assertEquals(tourist, touristAccountService.getUserById(tourist.getId()));
    }

    @Test
    public void getUsersByLastName() throws Exception {


        assertEquals(touristAccountService.getUsersByLastName(tourist2.getLastName()), Arrays.asList(tourist2));
    }

}