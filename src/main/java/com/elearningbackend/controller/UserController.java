package com.elearningbackend.controller;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.CustomStorageException;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.User;
import com.elearningbackend.service.AbstractUserService;
import com.elearningbackend.service.IStorageService;
import com.elearningbackend.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    @Qualifier("userService")
    private AbstractUserService<UserDto, String, User> abstractService;

    @Autowired
    private IStorageService userStorageService;

    @GetMapping("/users")
    @PreAuthorize(Constants.PRE_AUTH_ADMIN_USERS)
    public Pager<UserDto> loadAll(
            int page,
            int noOfRowInPage,
            String sortBy,
            String direction) {
        return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
    }
//    public Pager<UserDto> loadAll(
//            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
//            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
//            @RequestParam(defaultValue = SortingConstants.SORT_USER_DEFAULT_FIELD) String sortBy,
//            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
//        return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
//    }

    @GetMapping("/users/{key}")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<UserDto> getByKey(@PathVariable("key") String key) {
        try {
            UserDto userDto = abstractService.getOneByKey(key);

            Result<UserDto> result = new Result<UserDto>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), userDto);
            return result;

        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }

    @PostMapping("/users")
    @PreAuthorize(Constants.PRE_AUTH_ADMIN_USERS)
    public Result<UserDto> add(@Valid @RequestBody UserDto userDto) {
        try {
            ServiceUtils.checkDataMissing(userDto,
                    "username", "password", "email", "phone", "role");
            abstractService.add(userDto);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), userDto);
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), userDto);
        } catch (Exception e) {
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), userDto);
        }
    }

    @PostMapping(value = "/users/{key}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public Result<FileDto> uploadProfilePicture(
            @PathVariable("key") String key, @RequestParam("avatar") MultipartFile avatar, HttpServletResponse response) {
        CurrentUser currentUser = getCurrentUser();
        if (!checkCurrentUser(currentUser, key)) {
            return new Result<>(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage(), null);
        }
        try {
            FileDto fileDto = userStorageService.store(avatar, currentUser.getUsername());
            UserDto userDtoUpdated = abstractService.updateAvatar(fileDto.getUrl(), currentUser.getUsername());
            userStorageService.deleteFile(currentUser.getAvatar());
            SecurityUtil.resetToken(response, userDtoUpdated);
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), fileDto);
        } catch (CustomStorageException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }

    @GetMapping(value = "/users/{key}/upload")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<FileDto> uploadProfilePicture(@PathVariable("key") String username) {
        CurrentUser currentUser = getCurrentUser();
        if (!checkCurrentUser(currentUser, username)) {
            return new Result<>(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage(), null);
        }
        try {
            FileDto fileDto = userStorageService.loadFile(currentUser.getAvatar());
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), fileDto);
        } catch (CustomStorageException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }

    @PutMapping("/users/{key}")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<UserDto> edit(@PathVariable String key,
            @RequestBody UserDto userDto, HttpServletResponse response) {
        if (userDto.getActivated() != Constants.STATUS_ACTIVATED) {
            return new Result<>(Errors.INVALID_USER_DETAILS.getId(), Errors.INVALID_USER_DETAILS.getMessage(), userDto);
        }
        userDto.setUsername(key);
        CurrentUser currentUser = getCurrentUser();
        if (!checkCurrentUser(currentUser, key)) {
            return new Result<>(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage(), null);
        }
        try {
            ServiceUtils.checkDataMissing(userDto, "username", "password");
            abstractService.edit(userDto);
            UserDto userDtoToken = abstractService.edit(userDto);
            SecurityUtil.resetToken(response, userDtoToken);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), userDtoToken);
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), userDto);
        }
    }

    @DeleteMapping("/users/{key}")
    @PreAuthorize(Constants.PREAUTH_ADMINISTRATOR)
    public Result<UserDto> delete(@PathVariable("key") String key) {
        CurrentUser currentUser = getCurrentUser();
        if (currentUser.getUsername().equals(key)) {
            return new Result<>(Errors.CANNOT_DELETE_YOURSELF.getId(),
                    Errors.CANNOT_DELETE_YOURSELF.getMessage(), null);
        }
        try {
            abstractService.delete(key);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }

    @PutMapping("/users/change-role/{key}")
    @PreAuthorize(Constants.PREAUTH_ADMINISTRATOR)
    public Result<UserDto> changeRole(@PathVariable("key") String key,
            @RequestParam(value = "r") String role) {
        CurrentUser currentUser = getCurrentUser();
        if (currentUser.getUsername().equals(key)) {
            return new Result<>(Errors.CANNOT_CHANGE_ROLE_ADMIN.getId(),
                    Errors.CANNOT_CHANGE_ROLE_ADMIN.getMessage(), null);
        }
        try {
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(),
                    abstractService.updateRole(key, role));
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }
}
