package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ChangePassUserDto {

    @NotBlank(message = "Old password cannot empty")
    private String password;

    @NotBlank(message = "New password cannot empty")
    private String passwordNew;
}
