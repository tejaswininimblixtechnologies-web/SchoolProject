package com.nimblix.SchoolPEPProject.Service;

import java.util.Date;
import com.nimblix.SchoolPEPProject.Dto.AttendanceResponse;
public interface AttendanceReportService {

    AttendanceResponse getStudentAttendance(Integer studentId, Date from, Date to);
    AttendanceResponse getClassAttendance(String classId, Date from, Date to);
}
