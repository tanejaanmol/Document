package com.nineleaps.DocumentManagementSystem.controller;


import com.nineleaps.DocumentManagementSystem.dto.UploadRequestData;
import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import com.nineleaps.DocumentManagementSystem.service.Impl.UploadServiceImpl;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@Controller
public class UploadController {

    @Autowired
    UploadServiceImpl impl;


    @ResponseBody
    @PostMapping(value = "/v1/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomResponse> fetchData(@RequestHeader("tokenId") String tokenData, @RequestParam("file") MultipartFile multipartFile,@RequestParam("fileType") String filetype) throws IOException, ParseException {
        System.out.println(tokenData);
        return impl.storeData(tokenData,multipartFile, filetype);

    }


}
