package com.nineleaps.DocumentManagementSystem.service;

import com.nineleaps.DocumentManagementSystem.dto.UploadRequestData;
import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    public ResponseEntity<CustomResponse> storeData(String tokenData, MultipartFile file, String filetype) throws IOException, ParseException;
}
