package com.nimblix.SchoolPEPProject.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SubjectScoreDTO {

    private String subject;
    private int marks;
    private int maxMarks;
    private String examName;
    private String examDate; // ISO string
}
