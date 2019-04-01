package com.nineleaps.DocumentManagementSystem.service.Impl;



import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import com.nineleaps.DocumentManagementSystem.dto.SigninResponseData;
import com.nineleaps.DocumentManagementSystem.dto.TokenData;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInInvalidTokenError;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInUserDataNotFound;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeAccountsRepository;
import com.nineleaps.DocumentManagementSystem.service.SignInService;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SigninServiceImpl implements SignInService {

    @Autowired
    EmployeeAccountsRepository repo;



    @Override
    public SigninResponseData tokenValidationreq(String tokenData) throws IOException, ParseException {
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
        String email_verified = (String) json.get("email_verified");
        String unique_id = (String) json.get("sub");
        System.out.println(email);
        System.out.println(email_verified);
        System.out.println(unique_id);
        return authorizeUser(email, email_verified, unique_id);
    }

    @Override
    public SigninResponseData authorizeUser(String email, String email_verifier, String unique_id){

           EmployeeAccounts data;
        try {
           data = repo.findbyEmailId(email);
           //repo.updateGoogleId(unique_id, data.getUid());
       }
       catch(Exception e){

            throw new SignInUserDataNotFound("The User Records is not available in the database");

        }

        SigninResponseData packetData=new SigninResponseData();
        packetData.setEmailId(data.getEmailId());
        packetData.setEmailVerifier(email_verifier);
        packetData.setView(data.getDesignation());


        //CREATING A FOLDER FOR THE LOGGED IN USER
        String uid="/home/nineleaps/Desktop/UserData/"+data.getUid().toString();
        new File(uid).mkdir();




        return packetData;
    }

    }







