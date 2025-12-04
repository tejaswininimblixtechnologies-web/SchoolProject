package com.nimblix.SchoolPEPProject.Controller;



import com.nimblix.SchoolPEPProject.Request.AdminAccountCreateRequest;
import com.nimblix.SchoolPEPProject.Request.SubmitEmailRequest;
import com.nimblix.SchoolPEPProject.Service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // TASK 1: Submit Email
    @PostMapping("/submit-email")
    public ResponseEntity<String> submitEmail(@RequestBody SubmitEmailRequest request) {
        String response = adminService.submitEmail(request);
        return ResponseEntity.ok(response);
    }

    // TASK 2: Create Admin Account
    @PostMapping("/create-account")
    public ResponseEntity<String> createAdminAccount(@RequestBody AdminAccountCreateRequest request) {
        Integer adminId = adminService.createAdminAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Admin account created successfully. Admin ID: " + adminId);
    }

    
}
