package com.nineleaps.DocumentManagementSystem.service;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface DownloadService {
    ResponseEntity<Object> tokenValidationreq(String tokenData, String fileType) throws IOException, ParseException;
    ResponseEntity<Object> giveFile(String email,String fileType) throws FileNotFoundException;
}
