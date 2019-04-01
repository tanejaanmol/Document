package com.nineleaps.DocumentManagementSystem;

import com.nineleaps.DocumentManagementSystem.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class DocumentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SignInInvalidTokenError.class)
    public final ResponseEntity<CustomResponse> tokennotfound(SignInInvalidTokenError ex, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(new Date(), ex.getMessage(),
                request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SignInUserDataNotFound.class)
    public final ResponseEntity<CustomResponse> datanotfound(SignInUserDataNotFound ex, WebRequest webRequest) {
        CustomResponse customResponse = new CustomResponse(new Date(), ex.getMessage(), webRequest.
                getDescription(false), HttpStatus.FAILED_DEPENDENCY.getReasonPhrase());
        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.FAILED_DEPENDENCY);


    }

    @ExceptionHandler(ViewNoRecordFound.class)
    public final ResponseEntity<CustomResponse> nodataavailable(ViewNoRecordFound ex, WebRequest webRequest) {
        CustomResponse customResponse = new CustomResponse(new Date(), ex.getMessage(), webRequest.
                getDescription(false), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UploadError.class)
    public final ResponseEntity<CustomResponse> uploaderror(UploadError ex, WebRequest webRequest) {
        CustomResponse customResponse = new CustomResponse(new Date(), ex.getMessage(), webRequest.
                getDescription(false), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST);

    }
}








