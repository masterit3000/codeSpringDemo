package com.elearningbackend.utility;

import org.springframework.http.MediaType;

import java.util.*;

import static java.util.Arrays.asList;

import java.util.List;

public interface Constants {
    int ZERO = 0;
    String NA = "N/A";
    int NO_OF_ROWS_DEFAULT_VALUE = 10;
    String NO_OF_ROWS_DEFAULT_STRING_VALUE = "10";
    int CURRENT_PAGE_DEFAULT_VALUE = 0;
    String CURRENT_PAGE_DEFAULT_STRING_VALUE = "0";
    int DEFAULT_RANDOM_CHARACTER_LENGTH = 20;
    String DEFAULT_FETCH_TYPE_LESSION = "-1";
    int LENGTH_ANSWER_DEFAULT = 2;
    List<String> VALID_IMAGE_TYPE = asList(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, "image/jpg");
    long AVATAR_MAXIMUM_SIZE = 3145728;

    /**
     * Secret Authenticate and authorize
     */
    String SECRET = "pk78syiEByfpCRHp5GdA7K0Z4+4dvc1X73fibeF0yCdh3z4kvCOOobqfF/ewWQIbfQP+0wuBvH4S9sw9FD2OAA==";
    String TOKEN_PREFIX = "Bearer";
    long EXPIRATION_TIME = 3_600_000;

    /**
     * List authory config in controller
     */
    String PREAUTH_ADMINISTRATOR = "hasAuthority('0')";
    String PREAUTH_MANAGER = "hasAuthority('1')";
    String PREAUTH_TEACHER = "hasAuthority('2')";
    String PREAUTH_CONTRIBUTER = "hasAuthority('3')";
    String PREAUTH_USER = "hasAuthority('4')";
    String PRE_AUTHENTICATED = "isAuthenticated()";
    String PRE_AUTH_ADMIN_USERS = "hasAnyAuthority('0', '1', '2', '3')";

    String AUTH_ADMINISTRATOR = "0";
    String AUTH_MANAGER = "1";
    String AUTH_TEACHER = "2";
    String AUTH_CONTRIBUTER = "3";
    String AUTH_USER = "4";
    List<String> ROLES_LIST = asList("0", "1", "2", "3", "4");
    /**
     * list status user
     */
    int STATUS_ACTIVATED = 1;
    int STATUS_NOT_ACTIVATED = 0;
    int STATUS_LOCKED = 2;

    /**
     * Headers Response
     */
    String HEADER_AUTHORIZATION = "Authorization";


    /**
     * List URI allow anonymous
     */
    List<String> URL_ALLOW_ANONYMOUS =
            Arrays.asList("/api/v1/signup","/api/v1/verify","/api/v1/test","/api/v1/addquestion");

    /**
     * List question type
     */
    int Q_TYPE_CHOOSE_ONE = 1;
    int Q_TYPE_CHOOSE_MULTIPLE = 2;
    int Q_TYPE_PARAGRAPH = 3;
    int Q_TYPE_ENTER = 4;

    int FETCH_ALL_ANSWERS = -1;
    int FETCH_ANSWER_CORRECT = 1;
    int FETCH_ANSWER_INCORRECT = 0;
    int ANSWER_HIDDEN_RESULT = 999;

    int DEFAULT_IELTS_READING_AMOUNT = 40;
    int DEFAULT_TOEFL_READING_AMOUNT = 30;
    int DEFAULT_TOEIC_READING_AMOUNT = 25;
    String READING_IELTS = "READING-IELTS";
    String READING_TOEIC = "READING-TOEIC";
    String READING_TOEFL = "READING-TOEFL";

    int LESSION_FINISH = 0;
    int LESSION_UNFINISH = 1;
}
