package com.nimblix.SchoolPEPProject.Controller;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimblix.SchoolPEPProject.Dto.AttendanceResponse;
import com.nimblix.SchoolPEPProject.Dto.CustomReportRequest;
import com.nimblix.SchoolPEPProject.Dto.CustomReportResponse;
import com.nimblix.SchoolPEPProject.Dto.StudentPerformanceResponse;
import com.nimblix.SchoolPEPProject.Service.AttendanceReportService;
import com.nimblix.SchoolPEPProject.Service.CustomReportService;
import com.nimblix.SchoolPEPProject.Service.StudentPerformanceService;



@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private StudentPerformanceService studentPerformanceService;

    @Autowired
    private AttendanceReportService attendanceReportService;

    @Autowired
    private CustomReportService customReportService;

    private SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");

    // Task 1: Student Performance
    @GetMapping("/student-performance/{studentId}")
    public ResponseEntity<?> getStudentPerformance(@PathVariable("studentId") Integer studentId) {
        StudentPerformanceResponse resp = studentPerformanceService.getStudentPerformance(studentId);
        if (resp == null) {
            return ResponseEntity.status(404).body("Student not found or no performance data.");
        }
        return ResponseEntity.ok(resp);
    }

    // Task 2: Attendance - student-level
    @GetMapping("/attendance/student/{studentId}")
    public ResponseEntity<?> getStudentAttendance(
            @PathVariable("studentId") Integer studentId,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to) {

        Date fromDate = null;
        Date toDate = null;
        try {
            if (from != null && to != null) {
                fromDate = isoDate.parse(from);
                toDate = isoDate.parse(to);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parse error. Use yyyy-MM-dd");
        }
        AttendanceResponse resp = attendanceReportService.getStudentAttendance(studentId, fromDate, toDate);
        return ResponseEntity.ok(resp);
    }

    // Task 2: Attendance - class-level
    @GetMapping("/attendance/class/{classId}")
    public ResponseEntity<?> getClassAttendance(
            @PathVariable("classId") String classId,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to) {

        Date fromDate = null;
        Date toDate = null;
        try {
            if (from != null && to != null) {
                fromDate = isoDate.parse(from);
                toDate = isoDate.parse(to);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parse error. Use yyyy-MM-dd");
        }
        AttendanceResponse resp = attendanceReportService.getClassAttendance(classId, fromDate, toDate);
        return ResponseEntity.ok(resp);
    }

    // Task 3: Custom Report Builder
    @PostMapping("/custom-builder")
    public ResponseEntity<?> generateCustomReport(@RequestBody CustomReportRequest request) {
        CustomReportResponse resp = customReportService.generateCustomReport(request);
        return ResponseEntity.ok(resp);
    }
}
