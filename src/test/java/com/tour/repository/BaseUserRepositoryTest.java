package com.tour.repository;

import com.tour.model.BaseUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

import static com.tour.model.BaseUser.Role.ROLE_ADMIN;
import static com.tour.model.BaseUser.Role.ROLE_STAFF;
import static com.tour.model.BaseUser.Role.ROLE_USER;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BaseUserRepositoryTest {


   @Autowired
   private BaseUserRepository baseUserRepository;

   @Autowired
   private PasswordEncoder encoder;

   private   BaseUser user =  new BaseUser();

    @Before
    public void setUp() throws Exception {


        user.setFirstName("ADMIN");
        user.setLastName("ADMIN");
        user.setEmail("adminadmin@mail.com");
        user.setPassword(encoder.encode("12345678"));
        user.setUserType(BaseUser.UserType.TOURIST);
        user.setUserName("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(ROLE_USER, ROLE_STAFF, ROLE_ADMIN)));
       user = baseUserRepository.save(user);
    }








    @Test
    public void findByUserName() throws Exception {
       assertEquals(user , baseUserRepository.findByUserName(user.getUserName()));
    }



    @Test
    public void findByEmail() throws Exception {
        assertEquals(user,baseUserRepository.findByEmail(user.getEmail()));

    }




}