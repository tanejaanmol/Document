package com.nineleaps.DocumentManagementSystem.service.Impl;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInInvalidTokenError;
import com.nineleaps.DocumentManagementSystem.exceptions.ViewNoRecordFound;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeAccountsRepository;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeDataRepository;
import com.nineleaps.DocumentManagementSystem.service.ViewService;
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

import java.io.IOException;
import java.util.List;

@Component
public class ViewServiceImpl implements ViewService {

    @Autowired
    EmployeeDataRepository data_repo;
    @Autowired
    EmployeeAccountsRepository acc_repo;

    public List<EmployeeData> tokenValidationreq(String tokenData) throws IOException, ParseException {
        System.out.println(tokenData);
        CloseableHttpClient client = HttpClients.createDefault();

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
        return fetchViewData(email);
    }


    @Override
    public List<EmployeeData> fetchViewData(String email) {
        EmployeeAccounts employeeAccounts = acc_repo.findbyEmailId(email);
        List<EmployeeData> employeeData = data_repo.findByfolderUid(employeeAccounts.getUid().toString());
        System.out.println(employeeData.size());
        if (employeeData.size() == 0) {
            throw new ViewNoRecordFound("You Have Not Uploaded any Data Till Now!!");
        }

        return employeeData;
    }
}
