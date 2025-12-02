package com.nimblix.SchoolPEPProject.Repository;

import java.sql.Date;
import java.util.List;

import com.nimblix.SchoolPEPProject.Model.Score;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findByStudentId(Integer studentId);
    List<Score> findByStudentIdAndExamDateBetween(Integer studentId, Date from, Date to);
    List<Score> findByExamDateBetween(Date from, Date to);
    List<Score> findBySubject(String subject);
}
