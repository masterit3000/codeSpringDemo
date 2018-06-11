package com.elearningbackend.utility;

import org.springframework.util.StringUtils;

/**
 * Created by dohalong on 05/12/2017.
 */
public interface RegexUtil {
    String VALID_EMAIL_REGEX = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
    String VALID_PHONE_REGEX = "^[0]{1}[1,9]{1}[0-9]{8,9}$";
}
