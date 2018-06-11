package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.ChangePassUserDto;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.UserDto;

public interface IChangePasswordService {
    UserDto changePass(ChangePassUserDto changePassUserDto, CurrentUser currentUser) throws ElearningException;
}
