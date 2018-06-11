package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.repository.IQuestionBankRepository;
import com.elearningbackend.utility.Paginator;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JMockit.class)
public class QuestionBankServiceTest {
    @Tested
    private QuestionBankService questionBankService;

    @Injectable
    private IQuestionBankRepository iQuestionBankRepository;

    @Injectable
    private Paginator<QuestionBank, QuestionBankDto> paginator;

    @Injectable
    private ModelMapper mapper;

    @Test
    public void returnQuestionBankDtoPagerOnPaginateWhenParamsAreValid(
            @Mocked Page<QuestionBank> pager,
            @Mocked QuestionBankDto question1,
            @Mocked QuestionBankDto question2) throws Exception {
        Pager<QuestionBankDto> result = new Pager<>();
        List<QuestionBankDto> questionBankDtos = new ArrayList<>();
        questionBankDtos.add(question1);
        questionBankDtos.add(question2);
        List<QuestionBank> questionBanks = questionBankDtos.stream().map(a -> mapper.map(a, QuestionBank.class)).collect(Collectors.toList());
        result.setCurrentPage(3);
        result.setTotalRow(30);
        result.setNoOfRowInPage(10);
        result.setTotalPage(3);
        result.setResults(questionBankDtos);
        new Expectations(){{
            pager.getTotalElements(); result = 30;
            pager.getContent(); result = questionBanks;
        }};
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getCurrentPage(), is(3));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getNoOfRowInPage(), is(10));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getTotalPage(), is(3L));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getTotalRow(), is(30L));
    }

    @Test
    public void returnQuestionBankDtoPagerWithNoQuestionOnPaginateWhenParamsHaveNullValue(
            @Mocked Page<QuestionBank> pager) throws Exception {
        Pager<QuestionBankDto> result = new Pager<>();
        List<QuestionBankDto> questionBankDtos = new ArrayList<>();
        questionBankDtos.add(null);
        questionBankDtos.add(null);
        List<QuestionBank> questionBanks = questionBankDtos.stream().filter(Objects::nonNull).map(a -> mapper.map(a, QuestionBank.class)).collect(Collectors.toList());
        result.setCurrentPage(3);
        result.setTotalRow(30);
        result.setNoOfRowInPage(10);
        result.setTotalPage(3);
        result.setResults(questionBankDtos);
        new Expectations(){{
            pager.getTotalElements(); result = 30;
            pager.getContent(); result = questionBanks;
        }};
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getCurrentPage(), is(3));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getNoOfRowInPage(), is(10));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getTotalPage(), is(3L));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getTotalRow(), is(30L));
        assertThat(questionBankService.loadAll(3, 10, "question_code", "asc").getResults().size(), is(0));
    }

    @Test
    public void returnDefaultQuestionBankDtoPagerOnPaginateWhenIntParamsAreZero() throws Exception {
        questionBankService.loadAll(0, 0, "question_code", "asc");
    }
}
