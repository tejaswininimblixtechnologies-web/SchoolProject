package com.nimblix.SchoolPEPProject.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimblix.SchoolPEPProject.Dto.AttendanceResponse;
import com.nimblix.SchoolPEPProject.Dto.CustomReportRequest;
import com.nimblix.SchoolPEPProject.Dto.CustomReportResponse;
import com.nimblix.SchoolPEPProject.Dto.StudentPerformanceResponse;
import com.nimblix.SchoolPEPProject.Service.AttendanceReportService;
import com.nimblix.SchoolPEPProject.Service.CustomReportService;
import com.nimblix.SchoolPEPProject.Service.StudentPerformanceService;

@Service
public class CustomReportServiceImpl implements CustomReportService {

    @Autowired
    private AttendanceReportService attendanceReportService;

    @Autowired
    private StudentPerformanceService studentPerformanceService;

    private SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public CustomReportResponse generateCustomReport(CustomReportRequest request) {
        String type = request.getReportType();
        Map<String, Object> data = new HashMap<String, Object>();

        // parse date range if provided
        Date from = null;
        Date to = null;
        try {
            if (request.getDateRange() != null) {
                String fromS = request.getDateRange().get("from");
                String toS = request.getDateRange().get("to");
                if (fromS != null && toS != null) {
                    from = isoDate.parse(fromS);
                    to = isoDate.parse(toS);
                }
            }
        } catch (Exception e) {
            // ignore parse errors; leave from/to null
        }

        if ("attendance".equalsIgnoreCase(type)) {
            // support class or student filters
            Object classIdObj = request.getFilters() != null ? request.getFilters().get("classId") : null;
            Object studentIdObj = request.getFilters() != null ? request.getFilters().get("studentId") : null;
            if (studentIdObj != null) {
                Integer sid = null;
                if (studentIdObj instanceof Number) sid = ((Number) studentIdObj).intValue();
                else sid = Integer.valueOf(String.valueOf(studentIdObj));
                AttendanceResponse resp = attendanceReportService.getStudentAttendance(sid, from, to);
                data.put("attendance", resp);
            } else if (classIdObj != null) {
                String classId = String.valueOf(classIdObj);
                AttendanceResponse resp = attendanceReportService.getClassAttendance(classId, from, to);
                data.put("attendance", resp);
            } else {
                // fallback: overall attendance in date range
                AttendanceResponse resp = attendanceReportService.getClassAttendance("ALL", from, to);
                data.put("attendance", resp);
            }
        } else if ("student-performance".equalsIgnoreCase(type) || "performance".equalsIgnoreCase(type)) {
            Object studentIdObj = request.getFilters() != null ? request.getFilters().get("studentId") : null;
            if (studentIdObj != null) {
                Integer sid = null;
                if (studentIdObj instanceof Number) sid = ((Number) studentIdObj).intValue();
                else sid = Integer.valueOf(String.valueOf(studentIdObj));
                StudentPerformanceResponse spr = studentPerformanceService.getStudentPerformance(sid);
                data.put("studentPerformance", spr);
            } else {
                // no student specified - return message / empty
                data.put("message", "No studentId provided for student-performance report.");
            }
        } else {
            data.put("message", "Report type not supported: " + type);
        }

        CustomReportResponse response = new CustomReportResponse();
        response.setReportType(type);
        response.setData(data);
        return response;
    }
}
