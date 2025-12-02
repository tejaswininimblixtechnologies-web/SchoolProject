package com.nimblix.SchoolPEPProject.Dto;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomReportResponse {

    private String reportType;
    private Map<String, Object> data;
}
