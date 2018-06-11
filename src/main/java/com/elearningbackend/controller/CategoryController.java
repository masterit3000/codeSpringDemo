package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CategoryDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.Result;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ResultCodes;
import com.elearningbackend.utility.SortingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CategoryController extends BaseController {
    @Autowired
    @Qualifier("categoryService")
    private IAbstractService<CategoryDto, String> abstractService;

    @GetMapping("/categories")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Pager<CategoryDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_CATEGORY_SUBCATEGORY_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
        return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
    }

    @GetMapping("/categories/{key}")
    @PreAuthorize(Constants.PRE_AUTHENTICATED)
    public Result<CategoryDto> getByKey(@PathVariable("key") String key){
        try{
            CategoryDto categoryDto = abstractService.getOneByKey(key);
            return new Result<>(ResultCodes.OK.getCode(),
                ResultCodes.OK.getMessage(), categoryDto);
        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }
}
