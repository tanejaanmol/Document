package com.nineleaps.DocumentManagementSystem.service;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.dto.TokenData;
import com.nineleaps.DocumentManagementSystem.exceptions.ViewNoRecordFound;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public interface ViewService {

    public List<EmployeeData> fetchViewData(String email) throws ViewNoRecordFound;
    public List<EmployeeData> tokenValidationreq(String tokenData) throws IOException, ParseException;
}
