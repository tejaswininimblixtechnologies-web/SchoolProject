package com.nimblix.SchoolPEPProject.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SubmitEmailRequest {
    private String email;
}
