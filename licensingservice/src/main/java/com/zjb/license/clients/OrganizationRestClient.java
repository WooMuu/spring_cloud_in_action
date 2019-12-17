package com.zjb.license.clients;

import com.zjb.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zjb on 2019/12/12.
 */
@Component
public class OrganizationRestClient {

    @Autowired
    RestTemplate restTemplate;


    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.
                exchange("http://organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET, null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
