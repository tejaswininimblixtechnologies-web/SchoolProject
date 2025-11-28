package com.nimblix.SchoolPEPProject.Service;

import com.nimblix.SchoolPEPProject.Model.Student;
import com.nimblix.SchoolPEPProject.Request.StudentRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    ResponseEntity<?> registerStudent(StudentRegistrationRequest studentRegistrationRequest);

    Student getStudentListByStudentId(Integer studentId);
}
