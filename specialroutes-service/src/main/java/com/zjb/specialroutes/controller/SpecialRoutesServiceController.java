package com.zjb.specialroutes.controller;

import com.zjb.specialroutes.model.AbTestRoute;
import com.zjb.specialroutes.service.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zjb on 2020/1/8.
 */
@RestController
@RequestMapping("/route/")
public class SpecialRoutesServiceController {
    @Autowired
    AbTestingRouteService abTestingRouteService;

    @GetMapping("/abtesting/{serviceName}")
    public AbTestRoute getRotue(@PathVariable("serviceName") String serviceName) {
        return abTestingRouteService.getRoute(serviceName);
    }
}
