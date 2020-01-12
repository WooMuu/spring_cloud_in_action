package com.zjb.ssosvr.repository;

import com.zjb.ssosvr.model.UserOrganization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zjb on 2020/1/12.
 */
@Repository
public interface OrgUserRepository extends CrudRepository<UserOrganization, String> {

    UserOrganization findByUserName(String userName);
}
