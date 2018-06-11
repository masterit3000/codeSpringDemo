package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentUser {
    private String username;
    private String displayName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String role;
}

