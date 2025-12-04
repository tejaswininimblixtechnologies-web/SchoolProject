package com.nimblix.SchoolPEPProject.ServiceImpl;

import com.nimblix.SchoolPEPProject.Model.Admin;
import com.nimblix.SchoolPEPProject.Repository.AdminRepository;
import com.nimblix.SchoolPEPProject.Request.AdminAccountCreateRequest;
import com.nimblix.SchoolPEPProject.Request.SubmitEmailRequest;
import com.nimblix.SchoolPEPProject.Service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String submitEmail(SubmitEmailRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        String email = request.getEmail().trim();

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check if exists
        if (adminRepository.existsByEmail(email)) {
            return "Email already registered.";
        }

        return "Email accepted. Continue to account creation.";
    }

    @Override
    public Integer createAdminAccount(AdminAccountCreateRequest request) {

        // Validate fields
        if (request.getAdminMobileNo() == null || request.getAdminMobileNo().length() != 10) {
            throw new IllegalArgumentException("Invalid mobile number");
        }

        if (request.getAdminName() == null || request.getAdminName().isEmpty()) {
            throw new IllegalArgumentException("Admin name is required");
        }

        if (!request.getPassword().equals(request.getReEnterPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (adminRepository.existsByAdminMobileNo(request.getAdminMobileNo())) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        // Create entity
        Admin admin = new Admin();
        admin.setAdminName(request.getAdminName());
        admin.setAdminMobileNo(request.getAdminMobileNo());
        admin.setEmail(request.getEmail());
        admin.setDesignation(request.getDesignation());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);

        return savedAdmin.getAdminId();
    }

}
