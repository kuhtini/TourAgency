package com.tour.service.intefaces;

import com.tour.model.Guide;
import com.tour.service.GuideAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tour.utils.Creator.nextGuide;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


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


        guide = nextGuide();
        guide1 = nextGuide();
        guide2 = nextGuide();


        guideAccountService.saveUser(guide);
        guideAccountService.saveUser(guide1);
        guideAccountService.saveUser(guide2);
    }



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



        assertEquals(guide, guideAccountService.getUserByUserName(guide.getUserName()));
    }

    @Test
    public void getUserByEmail() throws Exception {


        assertEquals(guide, guideAccountService.getUserByEmail(guide.getEmail()));


    }

    @Test
    public void getUserLikeByEmail() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1, guide2)), guideAccountService.getUserLikeByEmail("@gmail.com"));

    }

    @Test
    public void getUserLikeByUserName() throws Exception {


        assertEquals(new ArrayList<>(Arrays.asList(guide, guide1, guide2)), guideAccountService.getUserLikeByUserName("r"));

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