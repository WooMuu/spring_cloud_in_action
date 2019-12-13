package com.zjb.organization.repository;

import com.zjb.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zjb on 2019/12/13.
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

}
