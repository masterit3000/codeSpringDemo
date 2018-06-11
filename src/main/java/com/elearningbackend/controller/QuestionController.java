package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.customexception.ElearningMapException;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.QuestionDto;
import com.elearningbackend.dto.Result;
import com.elearningbackend.service.AbstractQuestionService;
import com.elearningbackend.utility.ResultCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController extends BaseController{

    @Autowired
    private AbstractQuestionService<QuestionDto,String> questionService;

    @GetMapping("/questions/{key}")
    public Result<QuestionDto> getOneByKey(@PathVariable String key){
        try {
            return new Result<>(ResultCodes.OK.getCode(),ResultCodes.OK.getMessage(),
                    questionService.getOneByKey(key));
        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(),e.getMessage(),null);
        }
    }

    @PostMapping("/questions")
    public Result add(@Valid @RequestBody List<QuestionDto> questionDtos){
        CurrentUser currentUser = getCurrentUser();
        questionDtos.stream().forEach(e->{
            e.getQuestionBankDto().setCreatorUsername(currentUser.getUsername());
            e.getAnswerDtos().stream().forEach(w-> w.getAnswerBankDto().setCreatorUsername(currentUser.getUsername()));
        });
        try {
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(),questionService.add(questionDtos));
        }catch (ElearningMapException e){
            e.printStackTrace();
            return new Result<>(e.getErrorCode(),e.getMessage(),e.getExceptionMap());
        }catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(),e.getMessage(),null);
        }
    }

    @PutMapping("/questions")
    public Result<QuestionDto> edit(@Valid @RequestBody QuestionDto questionDto){
        CurrentUser currentUser = getCurrentUser();
        questionDto.getQuestionBankDto().setLastUpdaterUsername(currentUser.getUsername());
        questionDto.getAnswerDtos().stream().forEach(e->{
            if(e.getAnswerBankDto().getAnswerCode()!=null)
                e.getAnswerBankDto().setLastUpdaterUsername(currentUser.getUsername());
            else
                e.getAnswerBankDto().setCreatorUsername(currentUser.getUsername());
        });
        try {

            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(),questionService.edit(questionDto));
        }catch (ElearningMapException e){
            e.printStackTrace();
            return new Result(e.getErrorCode(),e.getMessage(),e.getExceptionMap());
        }catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(),e.getMessage(),null);
        }
    }

    @DeleteMapping("/questions/{key}")
    public Result<QuestionDto> delete(@PathVariable String key){
        try {
            return new Result<>(ResultCodes.OK.getCode(),ResultCodes.OK.getMessage(),
                    questionService.delete(key));
        }catch (ElearningMapException e){
            e.printStackTrace();
            return new Result(e.getErrorCode(),e.getMessage(),e.getExceptionMap());
        }catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(),e.getMessage(),null);
        }
    }
}
