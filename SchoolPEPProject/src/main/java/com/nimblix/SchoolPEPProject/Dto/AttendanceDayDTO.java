package com.nimblix.SchoolPEPProject.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttendanceDayDTO {

    private String date; // ISO date
    private int presentCount;
    private int absentCount;
    private double attendancePercent;
    private int lateCount;
    private int earlyLeaveCount;
}
