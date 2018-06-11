package com.elearningbackend.dto;

import com.elearningbackend.utility.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
@NoArgsConstructor
public class VerificationDto {
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Activation Digest cannot be empty!")
    @JsonProperty("activation_digest")
    private String activationDigest;

    @JsonDeserialize(using=CustomDateAndTimeDeserialize.class)
    @JsonProperty("activated_at")
    private Date activatedAt;
}
