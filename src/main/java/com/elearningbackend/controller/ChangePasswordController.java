package com.elearningbackend.controller;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.ChangePassUserDto;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.service.IChangePasswordService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ResultCodes;
import com.elearningbackend.utility.SecurityUtil;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class ChangePasswordController extends BaseController {
    @Autowired
    private IChangePasswordService changePassword;

    @PostMapping("change-pass")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<Object> changePass(
            @Valid @RequestBody ChangePassUserDto changePassUserDto, HttpServletResponse response
    ){
        CurrentUser currentUser = getCurrentUser();
        if (currentUser.getUsername() == null){
            return new Result<>(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage(), null);
        }
        try {
            ServiceUtils.checkDataMissing(changePassUserDto, "password", "passwordNew");
            UserDto userDto = changePassword.changePass(changePassUserDto, currentUser);
            SecurityUtil.resetToken(response, userDto);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        }catch(ElearningException e){
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }
}
