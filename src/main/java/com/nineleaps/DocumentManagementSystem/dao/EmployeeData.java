package com.nineleaps.DocumentManagementSystem.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.UUID;

@Table(value ="EmployeeData")
public class EmployeeData {
    @Id
    @PrimaryKeyColumn(name = "uid")
    UUID uid=UUID.randomUUID();

    @Column(value="filetype")
    private String fileType;

    @Column(value="folderuid")
    private String folderUid;


    @Column(value="originalname")
    private String orignalName;

    @Column(value="uploadedby")
    private String uploadedBy;

    @Column(value="uploadtime")
    private long uploadTime;

    public EmployeeData(UUID uid, String fileType, String folderUid, String orignalName, String uploadedBy, long uploadTime) {
        this.uid = uid;
        this.fileType = fileType;
        this.folderUid = folderUid;
        this.orignalName = orignalName;
        this.uploadedBy = uploadedBy;
        this.uploadTime = uploadTime;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFolderUid() {
        return folderUid;
    }

    public void setFolderUid(String folderUid) {
        this.folderUid = folderUid;
    }

    public String getOrignalName() {
        return orignalName;
    }

    public void setOrignalName(String orignalName) {
        this.orignalName = orignalName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}