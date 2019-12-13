package com.zjb.license.controllers;

import com.zjb.license.model.License;
import com.zjb.license.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    @Autowired
    private LicenseService licenseService;

//    @Autowired
//    private ServiceConfig serviceConfig;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {

        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {

        return licenseService.getLicense(organizationId, licenseId);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
    public String updateLicenses(@PathVariable("licenseId") String licenseId) {
        return String.format("This is the put");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @RequestMapping(value = "{licenseId}/{clientType}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses(@PathVariable("licenseId") String licenseId) {
        return String.format("This is the Delete");
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClientType(@PathVariable("licenseId") String licenseId, @PathVariable("organizationId") String organizationId, @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

}
