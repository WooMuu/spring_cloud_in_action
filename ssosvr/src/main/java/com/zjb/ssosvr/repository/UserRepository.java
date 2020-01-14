package com.zjb.ssosvr.repository;

import com.zjb.ssosvr.model.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zjb on 2020/1/14.
 */
@Repository
public interface UserRepository extends CrudRepository<SysUser, String> {

    SysUser getByUsername(String userName);
}
