package com.elearningbackend.controller;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.SortingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionBankController {
    @Autowired
    @Qualifier("questionBankService")
    private IAbstractService<QuestionBankDto, String> abstractService;

    @GetMapping("/questions-bank")
    public Pager<QuestionBankDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_QUESTION_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
        Pager<QuestionBankDto> questionBankDtoPager = abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
        questionBankDtoPager.getResults().stream().forEach(e->e.getSubcategory().setQuestionBanks(null));
       return questionBankDtoPager;
    }

    @GetMapping("/questions-bank/{key}")
    public QuestionBankDto getOne(@PathVariable("key") String key){
        return null;
    }

    @GetMapping("/questions-bank/{creatorUsername}")
    public Pager<QuestionBankDto> getByCreator(
            @PathVariable("creatorUsername") String creatorUsername,
            @RequestParam("page") int page,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        return null;
    }

    @PostMapping("/questions-bank")
    public String add(@RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    @PutMapping("/questionsbank/{key}")
    public String edit(@PathVariable String key, @RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    @DeleteMapping("/questions-bank/{key}")
    public String delete(@PathVariable String key){
        return null;
    }
}
