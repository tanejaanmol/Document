package com.nineleaps.DocumentManagementSystem.repository;



import com.nineleaps.DocumentManagementSystem.dao.EmployeeAccounts;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface EmployeeAccountsRepository extends CassandraRepository<EmployeeAccounts, String> {


    @Query("Select * from EmployeeAccounts where emailid=?0 ALLOW FILTERING")
    EmployeeAccounts findbyEmailId(String email);



    @Query("update EmployeeAccounts SET googleid=?0 WHERE uid=?1")
    void updateGoogleId(String googleid, UUID uid);

}