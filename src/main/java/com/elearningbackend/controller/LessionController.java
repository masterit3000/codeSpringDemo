package com.elearningbackend.controller;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.service.ILessionService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ResultCodes;
import com.elearningbackend.utility.ServiceUtils;
import com.elearningbackend.utility.SortingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@CrossOrigin
public class LessionController extends BaseController {
    @Autowired
    private ILessionService lessionService;
    @Autowired
    private IAbstractService<UserDto, String> userService;

    @PostMapping(value = "lessions", consumes = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<LessionDto> startLessions(@RequestBody String subcategoryCode){
        CurrentUser currentUser = getCurrentUser();
        if (StringUtils.isEmpty(subcategoryCode))
            return new Result<>(Errors.HAS_TO_CHOOSE_SUBCATE.getId(), Errors.HAS_TO_CHOOSE_SUBCATE.getMessage(), null);
        try {
            LessionDto lessionDto = lessionService.startLession(userService.getOneByKey(currentUser.getUsername()), subcategoryCode);
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), lessionDto);
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), null);
        }
    }

    @GetMapping(value = "lessions")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<Pager<LessionDto>> getAllLession(
        @RequestParam(value = "finish", defaultValue = Constants.DEFAULT_FETCH_TYPE_LESSION) int isFinish,
        @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
        @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
        @RequestParam(defaultValue = SortingConstants.SORT_LESSIONS_DEFAULT_FIELD) String sortBy,
        @RequestParam(defaultValue = SortingConstants.ASC) String direction) {
        CurrentUser currentUser = getCurrentUser();
        Pager<LessionDto> lessionDtoPager = null;
        try {
            lessionDtoPager = lessionService.loadAll(currentUser, isFinish, page, noOfRowInPage, sortBy, direction);
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
        return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), lessionDtoPager);
    }

    @GetMapping(value = "lessions/{key}")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<LessionDto> getLession(@PathVariable("key") String key){
        CurrentUser currentUser = getCurrentUser();
        try {
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), lessionService.getOneByKey(key));
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), null);
        }
    }

    @PutMapping(value = "lessions/{key}")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<LessionDto> editLession(
        @PathVariable("key") String key, @RequestBody LessionDto lessionDto){
        CurrentUser currentUser = getCurrentUser();
        try {
            ServiceUtils.checkDataMissing(lessionDto, "mappedLessionReports");
            LessionDto lessionDto1 = lessionService.edit(userService.getOneByKey(currentUser.getUsername()), key, lessionDto);
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), lessionDto1);
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), null);
        }
    }
}
