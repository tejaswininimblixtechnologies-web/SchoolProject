package com.nimblix.SchoolPEPProject.Dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttendanceResponse {

    private String scope; // "student" or "class"
    private String id;    // studentId or classId
    private List<AttendanceDayDTO> daily;
    private double overallAttendancePercent;
    private int totalPresent;
    private int totalAbsent;
}
