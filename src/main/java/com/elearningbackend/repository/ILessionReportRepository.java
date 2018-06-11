package com.elearningbackend.repository;

import com.elearningbackend.entity.LessionReport;
import com.elearningbackend.entity.LessionReportId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dohalong on 22/12/2017.
 */
public interface ILessionReportRepository extends JpaRepository<LessionReport, LessionReportId>{
}
