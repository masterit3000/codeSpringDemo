package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.LessionDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.UserDto;

public interface ILessionService {
    LessionDto startLession(UserDto userDto, String subcategoryCode) throws ElearningException;
    Pager<LessionDto> loadAll(CurrentUser currentUser, int isFinish, int currentPage, int noOfRowInPage, String sortBy, String direction) throws ElearningException;
    LessionDto getOneByKey(String lessionCode) throws ElearningException;
    LessionDto edit(UserDto userDto, String lessionCode, LessionDto lessionDto) throws ElearningException;
}
