package com.nineleaps.DocumentManagementSystem.dto;

import org.springframework.lang.NonNull;

public class UploadRequestData {

    private String fileType;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    private long uploadTime;
}
