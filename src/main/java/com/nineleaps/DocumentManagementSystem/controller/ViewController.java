package com.nineleaps.DocumentManagementSystem.controller;


import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.service.Impl.ViewServiceImpl;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@Controller
public class ViewController {
    @Autowired
    ViewServiceImpl impl;

    @ResponseBody
    @GetMapping("/v1/view")
    public List<EmployeeData> getView(@RequestHeader(value = "tokenId") String tokenData) throws IOException, ParseException {
        return impl.tokenValidationreq(tokenData);
    }

}
