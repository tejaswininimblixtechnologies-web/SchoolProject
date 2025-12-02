package com.nimblix.SchoolPEPProject.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimblix.SchoolPEPProject.Dto.AttendanceDayDTO;
import com.nimblix.SchoolPEPProject.Dto.AttendanceResponse;
import com.nimblix.SchoolPEPProject.Model.Attendance;
import com.nimblix.SchoolPEPProject.Repository.AttendanceRepository;
import com.nimblix.SchoolPEPProject.Service.AttendanceReportService;

@Service
public class AttendanceReportServiceImpl implements AttendanceReportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    private SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public AttendanceResponse getStudentAttendance(Integer studentId, Date from, Date to) {
        List<Attendance> records;
        if (from != null && to != null) {
            records = attendanceRepository.findByStudentIdAndAttendanceDateBetween(studentId, from, to);
        } else {
            records = attendanceRepository.findByStudentId(studentId);
        }
        return buildAttendanceResponse("student", String.valueOf(studentId), records);
    }

    @Override
    public AttendanceResponse getClassAttendance(String classId, Date from, Date to) {
        List<Attendance> records;
        if (from != null && to != null) {
            records = attendanceRepository.findByClassIdAndAttendanceDateBetween(classId, from, to);
        } else {
            records = attendanceRepository.findByClassId(classId);
        }
        return buildAttendanceResponse("class", classId, records);
    }

    private AttendanceResponse buildAttendanceResponse(String scope, String id, List<Attendance> records) {
        // group by date
        Map<String, List<Attendance>> byDate = new TreeMap<String, List<Attendance>>();
        for (Attendance a : records) {
            String d = a.getAttendanceDate() == null ? "unknown" : isoDate.format(a.getAttendanceDate());
            List<Attendance> list = byDate.get(d);
            if (list == null) {
                list = new ArrayList<Attendance>();
                byDate.put(d, list);
            }
            list.add(a);
        }

        List<AttendanceDayDTO> daily = new ArrayList<AttendanceDayDTO>();
        int totalPresent = 0;
        int totalAbsent = 0;
        int totalRecords = 0;

        for (String d : byDate.keySet()) {
            List<Attendance> list = byDate.get(d);
            int present = 0;
            int absent = 0;
            int late = 0;
            int early = 0;
            for (Attendance a : list) {
                if (a.isPresent()) present++; else absent++;
                if (a.isLateEntry()) late++;
                if (a.isEarlyLeave()) early++;
            }
            totalPresent += present;
            totalAbsent += absent;
            totalRecords += (present + absent);

            AttendanceDayDTO dto = new AttendanceDayDTO();
            dto.setDate(d);
            dto.setPresentCount(present);
            dto.setAbsentCount(absent);
            dto.setLateCount(late);
            dto.setEarlyLeaveCount(early);
            int denom = (present + absent) == 0 ? 1 : (present + absent);
            dto.setAttendancePercent(Math.round(((double) present / (double) denom) * 10000.0) / 100.0);
            daily.add(dto);
        }

        double overallPercent = totalRecords == 0 ? 0.0 : ((double) totalPresent / (double) totalRecords) * 100.0;
        AttendanceResponse response = new AttendanceResponse();
        response.setScope(scope);
        response.setId(id);
        response.setDaily(daily);
        response.setTotalPresent(totalPresent);
        response.setTotalAbsent(totalAbsent);
        response.setOverallAttendancePercent(Math.round(overallPercent * 100.0) / 100.0);
        return response;
    }
}
