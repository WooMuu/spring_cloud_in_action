package com.zjb.organization.service;

import com.zjb.organization.model.Organization;
import com.zjb.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public Organization getOrganization(String organizationId) {
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (organization.isPresent()) return organization.get();
        return new Organization();
    }

    @Transactional
    public void deleteById(String organizaitionId) {
        organizationRepository.deleteById(organizaitionId);
    }
}
