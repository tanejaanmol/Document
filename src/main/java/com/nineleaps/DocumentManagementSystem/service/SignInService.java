package com.nineleaps.DocumentManagementSystem.service;


import com.nineleaps.DocumentManagementSystem.dto.SigninResponseData;
import com.nineleaps.DocumentManagementSystem.dto.TokenData;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public interface SignInService {
    public SigninResponseData authorizeUser(String email, String email_verifier, String unique_id);
    public SigninResponseData tokenValidationreq(String tokenData) throws IOException, ParseException;



}
