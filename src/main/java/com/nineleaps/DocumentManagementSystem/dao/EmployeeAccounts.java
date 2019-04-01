package com.nineleaps.DocumentManagementSystem.dao;



import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;


@Table(value="EmployeeAccounts")
public class EmployeeAccounts {



    @Id
    @PrimaryKeyColumn(name = "uid")
    UUID uid= UUID.randomUUID();


    @Column(value="emailid")
    private String emailId;

    @Column(value = "employeeid")
    private String employeeId;

    @Column(value = "googleid")
    private String googleId;

    @Column(value = "dateofbirth")
    private long dateOfBirth;

    @Column(value = "dateofjoining")
    private long dateOfJoining;

    @Column(value = "designation")
    private String designation;

    @Column(value = "firstname")
    private String firstName;

    @Column(value = "lastname")
    private String lastName;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(long dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
