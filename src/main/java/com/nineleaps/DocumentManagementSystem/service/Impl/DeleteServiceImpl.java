package com.nineleaps.DocumentManagementSystem.service.Impl;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInInvalidTokenError;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeAccountsRepository;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeDataRepository;
import com.nineleaps.DocumentManagementSystem.service.DeleteService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@Service
public class DeleteServiceImpl implements DeleteService {
    @Autowired
    EmployeeAccountsRepository acc_repo;
    @Autowired
    EmployeeDataRepository data_repo;

    @Override
    public ResponseEntity<CustomResponse> tokenValidationreq(String tokenData, String fileType) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println(tokenData);
        System.out.println("\n");
        // String newIdToken = tokenIds.substring(8);

        //SENDIND THE TOKEN TO GOOGLE IN ORDER TO DECRYPT IT

        HttpGet request = new HttpGet("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + tokenData);
        CloseableHttpResponse response = null;
        response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (!(status >= 200 && status < 300)) {
            System.out.println("Unexpected response status: " + status);
            //GIVES ERROR IF THE TOKEN IS INVALID

            throw new SignInInvalidTokenError("the token provided is INVALID");
        }

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseString);

        //EXTRACTING DATA FROM THE RESPONSE

        JSONObject json = (JSONObject) new JSONParser().parse(responseString);
        String email = (String) json.get("email");

        System.out.println(email);

        return deleteRecord(email, fileType);
    }

    public ResponseEntity<CustomResponse> deleteRecord(String email, String fileType) {
        String message="Success";
        String details="the file was deleted sucessfully!";
        EmployeeAccounts employeeAccounts = acc_repo.findbyEmailId(email);
        EmployeeData employeeData = data_repo.findFileRow(fileType, employeeAccounts.getUid().toString());
        if (employeeData == null) {
             message = "NoData";
             details = "No data found to delete";

            System.out.println("No data found");
        } else {
            data_repo.deleteByUid(employeeData.getUid());
        }
        File file = new File("/home/nineleaps/Desktop/UserData/" + employeeAccounts.getUid().toString() + "/" + fileType);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }

        CustomResponse customResponse = new CustomResponse(new Date(), message,
                details, HttpStatus.ACCEPTED.getReasonPhrase());


        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.ACCEPTED);
    }
}



