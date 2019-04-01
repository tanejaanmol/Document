package com.nineleaps.DocumentManagementSystem.repository;

import com.nineleaps.DocumentManagementSystem.dao.EmployeeData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeDataRepository extends CassandraRepository<EmployeeData,String> {
    @Query(allowFiltering = true)
    public List<EmployeeData> findByfolderUid(String str);


    @Query("select * from employeedata where filetype=?0 and folderuid=?1 Allow Filtering")
    public EmployeeData findFileRow(String fileType, String uuid);

    @Query("delete from employeedata WHERE uid=?0")
    public void deleteByUid(UUID uid);
}
