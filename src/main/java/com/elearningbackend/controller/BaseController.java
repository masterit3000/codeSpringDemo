package com.elearningbackend.controller;

import com.elearningbackend.dto.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

    protected Authentication authentication;
    protected CurrentUser currentUser;

    /**
     * Method get Current User from Context
     * Can't call this method in Contructor because Context is null.
     * Each Action in Controller, Call this method to get Current User
     * @return UserDTO - CurrentUser
     */
    protected CurrentUser getCurrentUser(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CurrentUser) authentication.getPrincipal();
    }

    protected boolean checkCurrentUser(CurrentUser currentUser, String username){
        return currentUser != null && currentUser.getUsername().equals(username);
    }

    /**
     * Config authorize for action in controller
     * We has for role in this project:
     * 0: Administrator
     * 1: Manager
     * 2: Teacher
     * 3: Contributer
     * 4: User
     *
     * ++++ One Role
     * With action in controller for users have role 0
     * Add before that action: @PreAuthorize("hasAuthority('0')")
     *
     * ++++ Many Role
     * With action in controller for users have role 0 and 1
     * Add before that action: @PreAuthorize("hasAuthority('0') or hasAuthority('1')")
     *
     *
     * ++++ All Role
     * With action in controller for all users
     * Add before that action: @PreAuthorize("hasAnyAuthority()")
     */
}
