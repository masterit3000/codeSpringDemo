package com.elearningbackend.service;

import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.utility.Paginator;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class UserServiceTest {

    @Tested
    private UserService target;

    @Injectable
    private IUserRepository repository;

    @Injectable
    private Paginator<User, UserDto> paginator;

    @Test
    public void edit() throws Exception {
//        UserDto userDto = new UserDto();
//        userDto.setUsername("hien01");
//        userDto.setPasswordDigest("123456");
//        userDto.setPhone("1234567");
//
//        UserDto userDtoCheck = new UserDto();
//        userDtoCheck.setUsername("hien01");
//        userDtoCheck.setPasswordDigest("123456");
//        userDtoCheck.setPhone("000000");
//
//        assertThat(target.validateUserDto(userDto, userDtoCheck), is(true));
    }

}