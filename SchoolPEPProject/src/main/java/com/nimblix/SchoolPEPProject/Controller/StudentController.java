package com.nimblix.SchoolPEPProject.Controller;

<<<<<<< Updated upstream
import org.springframework.http.HttpStatus;
=======
import com.nimblix.SchoolPEPProject.Request.StudentLoginRequest;
import com.nimblix.SchoolPEPProject.Request.StudentRegistrationRequest;
import com.nimblix.SchoolPEPProject.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimblix.SchoolPEPProject.Request.StudentLoginRequest;
import com.nimblix.SchoolPEPProject.Request.StudentRegistrationRequest;
import com.nimblix.SchoolPEPProject.Service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    /*
    In this API we are registering the student. It will help to onboard the student, In this we are storing the  student
    fullname,emailId and password.
     */
    @PostMapping("/register")
    public ResponseEntity<?> studentRegistration(@RequestBody StudentRegistrationRequest request){
        System.out.println("Controller Hit!");
        studentService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.OK).body("Student Registration Successful");
    }
    
    @GetMapping("/login")
    public ResponseEntity<?> studentLogin(@RequestBody StudentLoginRequest studentLoginRequest){
    	studentService.loginStudent(studentLoginRequest);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Student Login Successful");
    }
    
    public ResponseEntity<?> studentLogin(@RequestBody StudentLoginRequest studentLoginRequest){
    	return studentService.loginStudent(studentLoginRequest);
    }

    @GetMapping("/studentlist")
    public ResponseEntity<?> getStudentListBySchoolId(@RequestParam Integer schoolId){
        studentService.getStudentListBySchoolId(schoolId);
        return null;
    }


}
