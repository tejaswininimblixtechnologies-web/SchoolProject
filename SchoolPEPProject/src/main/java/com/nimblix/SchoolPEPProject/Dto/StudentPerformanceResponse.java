package com.nimblix.SchoolPEPProject.Dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentPerformanceResponse {

    private Integer studentId;
    private String studentName;
    private List<SubjectScoreDTO> subjectScores;
    private double overallPercentage;
    private String grade;         // A, B, C style
    private int ranking;          // ranking within the class/school (basic)
    private List<ImprovementTrendDTO> improvementTrends;

}
