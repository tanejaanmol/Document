package com.nineleaps.DocumentManagementSystem.service.Impl;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import com.nineleaps.DocumentManagementSystem.exceptions.SignInInvalidTokenError;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeAccountsRepository;
import com.nineleaps.DocumentManagementSystem.repository.EmployeeDataRepository;
import com.nineleaps.DocumentManagementSystem.service.DownloadService;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DownloadServiceImpl implements DownloadService {
    @Autowired
    EmployeeAccountsRepository acc_repo;
    @Autowired
    EmployeeDataRepository data_repo;


   @Override
   public ResponseEntity<Object> tokenValidationreq(String tokenData,String fileType) throws IOException, ParseException {

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
       return giveFile(email, fileType);
   }


   @Override
       public ResponseEntity<Object> giveFile(String email,String fileType) throws FileNotFoundException {
       EmployeeAccounts employeeAccounts=acc_repo.findbyEmailId(email);

       String filename="/home/nineleaps/Desktop/UserData/"+employeeAccounts.getUid().toString()+"/"+fileType;
       File file=new File(filename);
       InputStreamResource resource=new InputStreamResource(new FileInputStream(file));
       HttpHeaders headers=new HttpHeaders();
       headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",file.getName()));
       headers.add("Cache-Control","no-cache,no-store,must revalidate");
       headers.add("Pragma","no-cache");
       headers.add("Expires","0");
       ResponseEntity<Object> responseEntity=ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("aplication/pdf")).body(resource);
       return responseEntity;

    }
}
