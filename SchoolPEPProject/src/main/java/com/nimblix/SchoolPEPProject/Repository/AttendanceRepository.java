package com.nimblix.SchoolPEPProject.Repository;

import com.nimblix.SchoolPEPProject.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByStudentIdAndAttendanceDateBetween(Integer studentId, Date from, Date to);
    List<Attendance> findByStudentId(Integer studentId);
    List<Attendance> findByClassIdAndAttendanceDateBetween(String classId, Date from, Date to);
    List<Attendance> findByClassId(String classId);
    List<Attendance> findByAttendanceDateBetween(Date from, Date to);
}