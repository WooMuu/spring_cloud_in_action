package com.zjb.license.clients;

import com.zjb.license.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by zjb on 2019/12/13.
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    @GetMapping(value = "/v1/organizations/{organizationId}", consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
