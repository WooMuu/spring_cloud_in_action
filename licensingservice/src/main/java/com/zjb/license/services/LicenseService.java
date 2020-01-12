package com.zjb.license.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zjb.license.clients.OrganizationDiscoveryClient;
import com.zjb.license.clients.OrganizationFeignClient;
import com.zjb.license.clients.OrganizationRestClient;
import com.zjb.license.model.License;
import com.zjb.license.model.Organization;
import com.zjb.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
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
        return license;
    }

    public static void randomlyRunLong() {
        Random random = new Random();
//        int randomNum = random.nextInt((3 - 1) + 1) + 1;
        int randomNum = 3;
        if (randomNum == 3) sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @HystrixCommand(
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")},
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",//表明要建立一个线程池
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "30"), @HystrixProperty(name = "maxQueueSize", value = "10")})
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId).withProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
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
        if (license == null) {
            license = new License();
        }
        return license.withOrganizationId(org.getId() == null ? "" : org.getId());

    }

    public Organization retrieveOrgInfo(String organizationId, String clientType) {
        Assert.hasText(clientType, "clientType can not be null !!!");
        Organization organization = null;
        switch (clientType) {
            case "discovery":
                System.out.println("I am using the discovery client");
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
