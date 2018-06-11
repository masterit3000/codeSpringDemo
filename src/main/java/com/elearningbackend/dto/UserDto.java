package com.elearningbackend.dto;

import com.elearningbackend.utility.CustomDateAndTimeDeserialize;
import com.elearningbackend.utility.RegexUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Length(min = 6, max = 30, message = "Password has to be between 6-30")
    private String password;

    private String activationDigest;

    private Date activatedAt;

    private String rememberDigest;

    private String resetDigest;

    private Date resetSentAt;

    private String displayName;

    private int activated;

    @NotBlank(message = "Email cannot be blank!")
    @Pattern(regexp = RegexUtil.VALID_EMAIL_REGEX, message = "Email format is incorrect")
    private String email;

    @Pattern(regexp = RegexUtil.VALID_PHONE_REGEX, message = "Phone format is incorrect")
    private String phone;
    private String address;
    private String avatar;

    private String role;

    @JsonDeserialize(using=CustomDateAndTimeDeserialize.class)
    private Date createdAt;

    @JsonDeserialize(using=CustomDateAndTimeDeserialize.class)
    private Date updatedAt;

    public UserDto(String username) {
        this.username = username;
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(String username, String displayName, String email, String phone, String address, String avatar, String role) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.role = role;
    }
}
