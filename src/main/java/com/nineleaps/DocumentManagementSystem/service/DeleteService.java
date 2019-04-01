package com.nineleaps.DocumentManagementSystem.service;

import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface DeleteService {
    public ResponseEntity<CustomResponse> tokenValidationreq(String tokenData,String fileType) throws IOException, ParseException;
    public ResponseEntity<CustomResponse> deleteRecord(String email, String fileType);
}
