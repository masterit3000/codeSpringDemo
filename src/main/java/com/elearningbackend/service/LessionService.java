package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.Lession;
import com.elearningbackend.entity.LessionReport;
import com.elearningbackend.entity.LessionReportId;
import com.elearningbackend.repository.ILessionReportRepository;
import com.elearningbackend.repository.ILessionRepository;
import com.elearningbackend.utility.CodeGenerator;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Transactional
public class LessionService implements ILessionService {

    @Autowired
    private AbstractQuestionService<QuestionDto, String> questionService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private ILessionRepository iLessionRepository;
    @Autowired
    private ILessionReportRepository iLessionReportRepository;
    Paginator<Lession, LessionDto> paginator = new Paginator<>(LessionDto.class);

    private ModelMapper mapper = new ModelMapper();

    @Override
    public LessionDto startLession(UserDto userDto, String subcategoryCode) throws ElearningException {
        LessionDto lessionDto = new LessionDto();
        String lessionCode = CodeGenerator.generateLessionCode();
        Set<LessionReportDto> lessionReportDtos = convertToLessionReportDto(questionService.getRandomQuestionDtos(subcategoryCode), lessionCode);
        if (lessionReportDtos == null || lessionReportDtos.size() <= 0) {
            throw new ElearningException(Errors.SUBCATEGORY_NOT_FOUND.getMessage(), Errors.SUBCATEGORY_NOT_FOUND.getId());
        }
        lessionDto.setLessionCode(lessionCode);
        lessionDto.setUserDto(userDto);
        lessionDto.setIsFinish(Constants.LESSION_UNFINISH);
        lessionDto.setCreationDate(new Timestamp(System.currentTimeMillis()));
        iLessionRepository.save(mapper.map(lessionDto, Lession.class));
        lessionDto.setMappedLessionReports(lessionReportDtos);
        lessionReportDtos.forEach(e -> {
            LessionReport lessionReport = mapper.map(e, LessionReport.class);
            try {
                lessionReport.setCorrectAnswers(ServiceUtils.convertListToJson(e.getCorrectAnswers()));
                lessionReport.setIncorrectAnswers(ServiceUtils.convertListToJson(e.getIncorrectAnswers()));
            } catch (ElearningException e1) {
                e1.printStackTrace();
            }
            iLessionReportRepository.save(lessionReport);
        });
        return lessionDto;
    }

    @Override
    public Pager<LessionDto> loadAll(CurrentUser currentUser, int isFinish, int currentPage, int noOfRowInPage, String sortBy, String direction) throws ElearningException {
        if (isFinish != Constants.LESSION_UNFINISH && isFinish != Constants.LESSION_FINISH){
            Page<Lession> pager = iLessionRepository.findAll(
                    Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
            checkAccess(currentUser, pager.getContent());
            return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
        }
        Page<Lession> pager = iLessionRepository.findAllByIsFinish(isFinish, Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
        if (pager.getContent() == null || pager.getContent().size() <= 0){
            return null;
        }
        checkAccess(currentUser, pager.getContent());
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    private void checkAccess(CurrentUser currentUser, List<Lession> lessionList) throws ElearningException {
        if(lessionList.stream()
            .noneMatch(e -> validateUserLession(e.getUser().getUsername(), currentUser))){
            throw new ElearningException(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage());
        }
    }

    private boolean validateUserLession(String lessionUsername, CurrentUser currentUser){
        return lessionUsername.equals(currentUser.getUsername()) || currentUser.getRole().equals(Constants.AUTH_ADMINISTRATOR);
    }

    @Override
    public LessionDto getOneByKey(String lessionCode) throws ElearningException{
        Lession lession = iLessionRepository.findOne(lessionCode);
        if (lession == null){
            throw new ElearningException(Errors.LESSION_NOT_FOUND.getId(), Errors.LESSION_NOT_FOUND.getMessage());
        }
        LessionDto lessionDto = mapper.map(lession, LessionDto.class);
        setAnswerDtoForLessionDto(lession, lessionDto);
        if(lession.getIsFinish() == Constants.LESSION_FINISH){
            lessionDto.setMappedLessionReportsFinish(convertToLessionReportDtoFinish(lessionDto.getMappedLessionReports()));
            lessionDto.setMappedLessionReports(null);
        }
        return lessionDto;
    }

    @Override
    public LessionDto edit(UserDto currentUser, String lessionCode, LessionDto lessionDto) throws ElearningException {
        Lession lession = iLessionRepository.findOne(lessionCode);
        if (lession == null){
            throw new ElearningException(Errors.LESSION_NOT_FOUND.getId(), Errors.LESSION_NOT_FOUND.getMessage());
        }
        lessionDto.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        lessionDto.setIsFinish(Constants.LESSION_FINISH);
        setAnswerDtoForLessionDto(lession, lessionDto);
        lessionDto.setMappedLessionReportsFinish(convertToLessionReportDtoFinish(lessionDto.getMappedLessionReports()));

        Map<LessionReportDtoFinish, List<AnswerDto>> correctAnswerDtoFromDBMap = new TreeMap<>();
        lessionDto.getMappedLessionReportsFinish().stream().filter(e -> e.getCorrectAnswers().size() > 0).forEach(e -> {
            correctAnswerDtoFromDBMap.put(e, e.getCorrectAnswers());
        });

        Map<LessionReportDto, List<AnswerDto>> userAnswerDtosFromClient = new TreeMap<>();
        List<LessionReportDto> filteredLessionReportDtos = lessionDto.getMappedLessionReports().stream().filter(e -> e.getUserAnswers() != null).collect(Collectors.toList());
        filteredLessionReportDtos.forEach(e -> userAnswerDtosFromClient.put(e, e.getUserAnswers()));

        if (userAnswerDtosFromClient.size() > 0) {
            userAnswerDtosFromClient.forEach((k,v) -> {
                switch (k.getQuestionType()){
                    case Constants.Q_TYPE_CHOOSE_ONE:
                        correctAnswerDtoFromDBMap.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().anyMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                    case Constants.Q_TYPE_CHOOSE_MULTIPLE:
                        userAnswerDtosFromClient.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().allMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                    case Constants.Q_TYPE_ENTER:
                        userAnswerDtosFromClient.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().anyMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                }
            });
        }
        lessionDto.setUserDto(currentUser);
        lessionDto.setMappedLessionReports(userAnswerDtosFromClient.keySet());
        lessionDto.setTotalPercent(calculateTotalPercent(lessionDto.getMappedLessionReports(), lessionDto.getMappedLessionReportsFinish()));
        Lession lession1 = iLessionRepository.save(mapper.map(lessionDto, Lession.class));
        lessionDto.getMappedLessionReports().forEach(e -> {
            LessionReport lessionReport = iLessionReportRepository.save(mapper.map(e, LessionReport.class));
        });
        return lessionDto;

    }

    private void setAnswerDtoForLessionDto(Lession lession, LessionDto lessionDto) {
        lessionDto.getMappedLessionReports().stream().filter(e -> e.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getLessionReportId().getLessionReportQuestionCode())).forEach(e -> {
            lession.getMappedLessionReports().stream().filter(e1 -> e1.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e1.getLessionReportId().getLessionReportQuestionCode())).forEach(w -> {
                if (e.getLessionReportId().getLessionReportQuestionCode().equals(w.getLessionReportId().getLessionReportQuestionCode())
                        && e.getLessionReportId().getLessionReportLessionCode().equals(w.getLessionReportId().getLessionReportLessionCode())){
                    try {
                        System.out.println("abc");
                        List<AnswerDto> correctAnswerDto = ServiceUtils.convertAnswerDtoJsonToList(w.getCorrectAnswers());
                        List<AnswerDto> incorrectAnswerDto = ServiceUtils.convertAnswerDtoJsonToList(w.getIncorrectAnswers());
                        e.setCorrectAnswers(correctAnswerDto);
                        e.setIncorrectAnswers(incorrectAnswerDto);
                        List<AnswerDto> answerDtos = new ArrayList<>();
                        answerDtos.addAll(incorrectAnswerDto);
                        answerDtos.addAll(correctAnswerDto);
                        e.setAnswers(new ArrayList<>(answerDtos));
                    } catch (ElearningException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        });
    }

    private Set<LessionReportDto> convertToLessionReportDto(List<QuestionDto> questionDtos, String lessionCode){
        return questionDtos.stream().map(e -> {
            LessionReportDto lessionReportDto = new LessionReportDto();
            lessionReportDto.setLessionReportId(new LessionReportId(lessionCode, e.getQuestionBankDto().getQuestionCode()));
            lessionReportDto.setQuestionContent(e.getQuestionBankDto().getQuestionContent());
            lessionReportDto.setQuestionType(e.getQuestionBankDto().getQuestionType());
            lessionReportDto.setQuestionParentCode(e.getQuestionBankDto().getQuestionParentCode());
            lessionReportDto.setQuestionPoint(e.getQuestionBankDto().getPoint());
            lessionReportDto.setSubcategoryCode(e.getQuestionBankDto().getSubcategory().getSubcategoryCode());
            if (e.getQuestionBankDto().getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getQuestionBankDto().getQuestionCode())) {
                lessionReportDto.setCorrectAnswers(
                    e.getAnswerDtos()
                        .stream().filter(w -> w.getSystemResultDto().getSystemResultIsCorrect() == Constants.FETCH_ANSWER_CORRECT).collect(Collectors.toList()));
                lessionReportDto.setIncorrectAnswers(
                    e.getAnswerDtos()
                        .stream().filter(w -> w.getSystemResultDto().getSystemResultIsCorrect() == Constants.FETCH_ANSWER_INCORRECT).collect(Collectors.toList()));
                lessionReportDto.setAnswers(hideAnswerResult(e.getAnswerDtos()));
            }
            return lessionReportDto;
        }).collect(Collectors.toSet());
    }

    private Set<LessionReportDtoFinish> convertToLessionReportDtoFinish(Set<LessionReportDto> lessionReportDtos) throws ElearningException{
        return lessionReportDtos.stream()
            .filter(e -> e.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getLessionReportId().getLessionReportQuestionCode())).map(e -> {
            LessionReportDtoFinish lessionReportDtoFinish = new LessionReportDtoFinish();
            lessionReportDtoFinish.setQuestionContent(e.getQuestionContent());
            lessionReportDtoFinish.setLessionReportId(e.getLessionReportId());
            lessionReportDtoFinish.setQuestionParentCode(e.getQuestionParentCode());
            lessionReportDtoFinish.setQuestionPoint(e.getQuestionPoint());
            lessionReportDtoFinish.setSubcategoryCode(e.getSubcategoryCode());
            lessionReportDtoFinish.setIncorrectAnswers(e.getIncorrectAnswers());
            lessionReportDtoFinish.setCorrectAnswers(e.getCorrectAnswers());
            if (e.getUserAnswers() != null){
                if (e.getUserAnswers().size() > 0) lessionReportDtoFinish.setUserPoint(e.getUserPoint());
            }
            return lessionReportDtoFinish;
        }).collect(Collectors.toSet());
    }

    private Double calculateTotalPercent(Set<LessionReportDto> lessionReportDtos, Set<LessionReportDtoFinish> lessionReportDtoFinishes){
        Double totalUserPoint = 0.0;
        Double totalQuestionPoint = lessionReportDtoFinishes.stream().mapToDouble(LessionReportDtoFinish::getQuestionPoint).sum();
        totalUserPoint = lessionReportDtos.stream().filter(e -> e.getUserPoint() != null).mapToDouble(LessionReportDto::getUserPoint).sum();
        if (totalQuestionPoint == 0.0) return 0.0;
        return (totalUserPoint / totalQuestionPoint) * 100;
    }

    private List<AnswerDto> hideAnswerResult(List<AnswerDto> answerDtos){
        return answerDtos.stream().peek(e -> e.getSystemResultDto().setSystemResultIsCorrect(Constants.ANSWER_HIDDEN_RESULT))
            .collect(Collectors.toList());
    }
}
