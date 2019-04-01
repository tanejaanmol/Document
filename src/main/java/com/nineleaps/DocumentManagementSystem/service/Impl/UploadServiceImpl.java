package com.nineleaps.DocumentManagementSystem.service.Impl;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.dto.UploadRequestData;
import com.nineleaps.DocumentManagementSystem.exceptions.CustomResponse;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInInvalidTokenError;
import com.nineleaps.DocumentManagementSystem.exceptions.UploadError;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeAccountsRepository;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeDataRepository;
import com.nineleaps.DocumentManagementSystem.service.UploadService;
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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class UploadServiceImpl implements UploadService {
    @Autowired
    EmployeeAccountsRepository acc_repo;
    @Autowired
    EmployeeDataRepository data_repo;


    @Override
    public ResponseEntity<CustomResponse> storeData(String tokenData, MultipartFile multipartFile, String filetype) throws IOException, ParseException {

        System.out.println(tokenData);
        System.out.println("\n");
        CloseableHttpClient client = HttpClients.createDefault();

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
        String email_verified = (String) json.get("email_verified");
        String unique_id = (String) json.get("sub");
        String user_name = (String) json.get("name");
        System.out.println(email);
        System.out.println(email_verified);
        System.out.println(unique_id);
        System.out.println(user_name);

// FETCHING THE REQUIRED EMAIL RECORD AND THEN USING THE UID TO STORE AS FOLDERUID IN EMPLOYEE DATA
        long time= Instant.now().toEpochMilli();

        EmployeeAccounts employeeAccounts = acc_repo.findbyEmailId(email);
        EmployeeData finddata = data_repo.findFileRow(filetype, employeeAccounts.getUid().toString());
        if (finddata != null) {
            EmployeeData employeeData = new EmployeeData(finddata.getUid(), filetype,
                    employeeAccounts.getUid().toString(), multipartFile.getOriginalFilename(), user_name,time
                    );
            data_repo.save(employeeData);
        } else {
            EmployeeData employeeData = new EmployeeData(UUID.randomUUID(), filetype,
                    employeeAccounts.getUid().toString(), multipartFile.getOriginalFilename(), user_name,
                    time);
            data_repo.save(employeeData);
        }


        //saving data to the system
        try {
            File content = new File("/home/nineleaps/Desktop/UserData/" +
                    employeeAccounts.getUid().toString() + "/" + filetype);
            content.createNewFile();
            FileOutputStream fout = new FileOutputStream(content);
            fout.write(multipartFile.getBytes());
            fout.close();
        } catch (Exception e) {
            throw new UploadError("There was some problem while uploading the file");
        }

        CustomResponse customResponse = new CustomResponse(new Date(), "Success",
                "the file was uploaded sucessfully!", HttpStatus.ACCEPTED.getReasonPhrase());
//

        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.ACCEPTED);
    }
}
