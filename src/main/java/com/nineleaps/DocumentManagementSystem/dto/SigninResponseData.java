package com.nineleaps.DocumentManagementSystem.dto;

public class SigninResponseData {

    private String emailId;
    private String emailVerifier;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailVerifier() {
        return emailVerifier;
    }

    public void setEmailVerifier(String emailVerifier) {
        this.emailVerifier = emailVerifier;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    private String view;


}
