package com.elearningbackend.security;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Result;
import com.elearningbackend.utility.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        try {
            if(Constants.URL_ALLOW_ANONYMOUS.contains(((HttpServletRequest)request).getRequestURI()))
                filterChain.doFilter(request,response);
            else
            {
                Authentication authentication = TokenAuthenticationService
                        .getAuthentication((HttpServletRequest)request);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                filterChain.doFilter(request,response);
            }
        }catch (ElearningException e){
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(new Result<>(e.getErrorCode(),e.getMessage(),null)));
        }
    }
}