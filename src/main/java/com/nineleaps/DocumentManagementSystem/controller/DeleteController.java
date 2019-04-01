package com.nineleaps.DocumentManagementSystem.controller;

import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import com.nineleaps.DocumentManagementSystem.service.Impl.DeleteServiceImpl;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class DeleteController {

    @Autowired
    DeleteServiceImpl impl;

    @ResponseBody
    @PostMapping("v1/delete")
    public ResponseEntity<CustomResponse> deleteRequest(@RequestHeader("tokenId") String tokenData, @RequestParam("fileType") String fileType) throws IOException, ParseException {
        System.out.println(fileType);
        return impl.tokenValidationreq(tokenData, fileType);
    }


}
