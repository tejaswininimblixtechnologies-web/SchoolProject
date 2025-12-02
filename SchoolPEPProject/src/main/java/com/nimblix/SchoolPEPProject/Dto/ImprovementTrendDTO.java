package com.nimblix.SchoolPEPProject.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ImprovementTrendDTO {

    private String examDate; // ISO date
    private double averagePercent;
}
