package com.nimblix.SchoolPEPProject.Dto;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomReportRequest {

    private String reportType; // "attendance", "student-performance", ...
    private Map<String, String> dateRange; // from, to in "yyyy-MM-dd"
    private Map<String, Object> filters; // classId, studentId, department, feeStatus,
}
