package com.zjb.organization.service;

import com.zjb.organization.model.Organization;
import com.zjb.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).get();
    }
}
