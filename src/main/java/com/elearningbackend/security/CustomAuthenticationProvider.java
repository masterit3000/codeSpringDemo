package com.elearningbackend.security;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningAuthException;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userService")
    private IAbstractService<UserDto, String> abstractService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        UserDto userDto;
        try {
            userDto = abstractService.getOneByKey(name);
        } catch (ElearningException e) {
            throw new ElearningAuthException(Errors.USER_NOT_FOUND.getId(),Errors.USER_NOT_FOUND.name());
        }
        if(!userDto.getPassword().equals(SecurityUtil.sha256(password)))
            throw new ElearningAuthException(Errors.USER_PASSWORD_NOT_MATCH.getId(),Errors.USER_PASSWORD_NOT_MATCH.name());
        if(userDto.getActivated()== Constants.STATUS_NOT_ACTIVATED)
            throw new ElearningAuthException(Errors.USER_NOT_ACTIVATED.getId(),Errors.USER_NOT_ACTIVATED.name());
        if(userDto.getActivated() == Constants.STATUS_LOCKED)
            throw new ElearningAuthException(Errors.USER_LOCKED.getId(),Errors.USER_LOCKED.name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userDto.getRole()));
        return new UsernamePasswordAuthenticationToken(name,password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
