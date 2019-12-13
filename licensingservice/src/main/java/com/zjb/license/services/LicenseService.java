package com.zjb.license.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zjb.license.clients.OrganizationDiscoveryClient;
import com.zjb.license.clients.OrganizationFeignClient;
import com.zjb.license.clients.OrganizationRestClient;
import com.zjb.license.model.License;
import com.zjb.license.model.Organization;
import com.zjb.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    OrganizationRestClient organizationRestClient;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
//        return license.withComment(config.getExampleProperty());
        return null;
    }

    private void randomlyRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @HystrixCommand
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.deleteById(license.getLicenseId());
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization org = retrieveOrgInfo(organizationId, clientType);
        return license.withOrganizationId(org.getId());
    }

    public Organization retrieveOrgInfo(String organizationId, String clientType) {
        Assert.hasText(clientType, "clientType can not be null !!!");
        Organization organization = null;
        switch (clientType) {
            case "discovery":
                System.out.println("I am using the feign client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                organization = organizationRestClient.getOrganization(organizationId);
                System.out.println("I am using the rest client");
                break;
            default:
                break;
        }
        return organization;
    }
}
