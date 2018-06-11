package com.elearningbackend.repository;

import com.elearningbackend.entity.Lession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessionRepository extends JpaRepository<Lession, String> {
    @Query("select a from Lession a where a.isFinish = ?1")
    Page<Lession> findAllByIsFinish(int isFinish, Pageable pageable);
}
