package com.zjb.organization.controllers;

import com.zjb.organization.model.Organization;
import com.zjb.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zjb on 2019/12/13.
 */
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return organizationService.getOrganization(organizationId);
    }

    @DeleteMapping("/{organizationId}")
    public Organization deleteById(@PathVariable("organizationId") String organizationId) {
        organizationService.deleteById(organizationId);
        return null;
    }
}
