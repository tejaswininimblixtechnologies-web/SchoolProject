package com.nimblix.SchoolPEPProject.Repository;

import com.nimblix.SchoolPEPProject.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    boolean existsByEmail(String email);

    boolean existsByAdminMobileNo(String mobile);

    Admin findByAdminMobileNo(String adminMobileNo);
}
