package com.zjb.specialroutes.service;

import com.zjb.specialroutes.model.AbTestRoute;
import com.zjb.specialroutes.repository.AbTestingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zjb on 2020/1/8.
 */
@Service
public class AbTestingRouteService {
    @Autowired
    AbTestingRouteRepository abTestingRouteRepository;

    public AbTestRoute getRoute(String serviceName) {
        return abTestingRouteRepository.findByServiceName(serviceName);
    }

    public void saveAbTestingRoute(AbTestRoute route) {

        abTestingRouteRepository.save(route);

    }

    public void updateRouteAbTestingRoute(AbTestRoute route) {
        abTestingRouteRepository.save(route);
    }

    public void deleteRoute(AbTestRoute route) {
        abTestingRouteRepository.deleteById(route.getServiceName());
    }
}
