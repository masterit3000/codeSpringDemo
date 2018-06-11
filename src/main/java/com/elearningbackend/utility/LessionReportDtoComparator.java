package com.elearningbackend.utility;

import com.elearningbackend.dto.LessionReportDto;

import java.util.Comparator;

/**
 * Created by dohalong on 23/12/2017.
 */
public class LessionReportDtoComparator implements Comparator<LessionReportDto> {
    @Override
    public int compare(LessionReportDto o1, LessionReportDto o2) {
        return o1.getLessionReportId().getLessionReportQuestionCode().compareTo(o2.getLessionReportId().getLessionReportQuestionCode());
    }
}
