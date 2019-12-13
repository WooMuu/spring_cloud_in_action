package com.zjb.organization.controllers;

import com.zjb.organization.model.Organization;
import com.zjb.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zjb on 2019/12/13.
 */
@RestController
@RequestMapping("v1/organizations")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return organizationService.getOrganization(organizationId);
    }
}
