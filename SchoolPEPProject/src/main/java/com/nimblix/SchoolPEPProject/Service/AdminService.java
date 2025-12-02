package com.nimblix.SchoolPEPProject.Service;

import com.nimblix.SchoolPEPProject.Request.AdminAccountCreateRequest;
import com.nimblix.SchoolPEPProject.Request.SubmitEmailRequest;

public interface AdminService {

    String submitEmail(SubmitEmailRequest request);

    Integer createAdminAccount(AdminAccountCreateRequest request);
}
