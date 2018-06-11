package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.Result;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.service.AbstractCustomService;
import com.elearningbackend.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
public class AnswerBankController {
    @Autowired
    @Qualifier("answerBankService")
    private AbstractCustomService<AnswerBankDto, String, AnswerBank> abstractService;

    @GetMapping("/answers-bank")
    public Pager<AnswerBankDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int currentPage,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_ANSWER_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction
    ){
        return abstractService.loadAll(currentPage, noOfRowInPage, sortBy, direction);
    }

    @GetMapping("/answers-bank/{key}")
    public Result<AnswerBankDto> getByKey(@PathVariable("key") String key){
        try {
            AnswerBankDto answerBankDto = abstractService.getOneByKey(key);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), answerBankDto);
        }catch(ElearningException e){
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }

    @GetMapping("/answers-bank/{creatorUsername}/")
    public Pager<AnswerBankDto> getByCreator(
            @PathVariable("creatorUsername") String creatorUserName,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        //TODO
        return null;
    }

    @PostMapping("/answers-bank")
    public Result<AnswerBankDto> add(@Valid @RequestBody AnswerBankDto answerBankDto){
        try {
            ServiceUtils.checkDataMissing(answerBankDto,
                    "answerContent", "creationDate", "creatorUsername");
            answerBankDto.setAnswerCode(CodeGenerator.generateAnswerCode());
            abstractService.add(answerBankDto);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), answerBankDto);
        }catch (ElearningException e){
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), answerBankDto);
        }catch (Exception e) {
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), answerBankDto);
        }
    }

    @PutMapping("/answers-bank/{key}")
    public Result<AnswerBankDto> edit(
            @Valid
            @PathVariable String key,
            @RequestBody AnswerBankDto answerBankDto){
      try {
          ServiceUtils.checkDataMissing(answerBankDto,
                  "answerContent", "creationDate", "lastUpdateDate", "creatorUsername", "lastUpdaterUsername");
          answerBankDto.setAnswerCode(key);
          abstractService.edit(answerBankDto);
          return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), answerBankDto);
      }catch (ElearningException e ) {
          e.printStackTrace();
          return new Result<>(e.getErrorCode(), e.getMessage(), answerBankDto);
      }catch (Exception ex) {
          ex.printStackTrace();
          return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                  ex.getMessage(), answerBankDto);
      }
    }

    @DeleteMapping("/answers-bank/{key}")
    public Result<AnswerBankDto> delete(@PathVariable("key") String key){
        try {
            abstractService.delete(key);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }
}
