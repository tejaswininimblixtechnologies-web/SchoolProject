package com.nimblix.SchoolPEPProject.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentLoginRequest {
	private String fullName;
    private String email;
    private String password;
}
