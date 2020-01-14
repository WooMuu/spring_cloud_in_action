package com.zjb.ssosvr.repository;

import com.zjb.ssosvr.model.SysPermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zjb on 2020/1/14.
 */
@Repository
public interface PermissionRepository extends CrudRepository<SysPermission, String> {
    List<SysPermission> findByUserName(String username);
}
