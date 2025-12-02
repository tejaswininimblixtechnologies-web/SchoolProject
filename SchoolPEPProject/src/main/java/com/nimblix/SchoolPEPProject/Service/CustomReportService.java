package com.nimblix.SchoolPEPProject.Service;

import com.nimblix.SchoolPEPProject.Dto.CustomReportRequest;
import com.nimblix.SchoolPEPProject.Dto.CustomReportResponse;
public interface CustomReportService {

    CustomReportResponse generateCustomReport(CustomReportRequest request);
}
