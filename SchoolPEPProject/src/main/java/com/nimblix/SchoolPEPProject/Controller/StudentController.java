package com.nimblix.SchoolPEPProject.Controller;

import com.nimblix.SchoolPEPProject.Request.StudentRegistrationRequest;
import com.nimblix.SchoolPEPProject.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> studentRegistration(@RequestBody StudentRegistrationRequest studentRegistrationRequest){
         studentService.registerStudent(studentRegistrationRequest);
         return  ResponseEntity.status(HttpStatus.CREATED).body("Student Registration Successful");
    }

}
