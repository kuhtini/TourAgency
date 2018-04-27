package com.tour.repository;

import com.tour.model.BaseUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static com.tour.model.BaseUser.Role.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BaseUserRepositoryTest {


    @Mock
   private BaseUserRepository baseUserRepository;

   @Autowired
   private PasswordEncoder encoder;

   private   BaseUser user =  new BaseUser();

    @Before
    public void setUp() throws Exception {


        when(baseUserRepository.save(user)).thenReturn(user);

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
        when(baseUserRepository.findByUserName(user.getUserName())).thenReturn(user);


       assertEquals(user , baseUserRepository.findByUserName(user.getUserName()));
    }



    @Test
    public void findByEmail() throws Exception {
        when(baseUserRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertEquals(user,baseUserRepository.findByEmail(user.getEmail()));

    }




}