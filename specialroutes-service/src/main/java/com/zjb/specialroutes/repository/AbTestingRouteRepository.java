package com.zjb.specialroutes.repository;

import com.zjb.specialroutes.model.AbTestRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zjb on 2020/1/8.
 */
@Repository
public interface AbTestingRouteRepository extends CrudRepository<AbTestRoute, String> {
    public AbTestRoute findByServiceName(String serviceName);
}
