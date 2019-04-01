package com.nineleaps.DocumentManagementSystem.controller;


import com.nineleaps.DocumentManagementSystem.dto.SigninResponseData;
import com.nineleaps.DocumentManagementSystem.dto.TokenData;
import com.nineleaps.DocumentManagementSystem.service.Impl.SigninServiceImpl;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin
@Controller
public class SignInController {
    @Autowired
    SigninServiceImpl impl;


    @ResponseBody
    @PostMapping("/v1/signin")
    public SigninResponseData signInRequest(@RequestBody String tokenData) throws IOException, ParseException {
        return impl.tokenValidationreq(tokenData);

}}







