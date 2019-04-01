package com.nineleaps.DocumentManagementSystem.exceptions;

import java.util.Date;

public class CustomResponse {
    private Date timestamp;
    private String message;
    private String details;
    private String httpCodeMessage;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }

    public CustomResponse(Date timestamp, String message, String details, String httpCodeMessage) {
        super();
        this.timestamp=timestamp;
        this.message = message;
        this.details = details;
        this.httpCodeMessage=httpCodeMessage;
    }
    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }

}
