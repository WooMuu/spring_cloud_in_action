package com.zjb.specialroutes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zjb on 2020/1/8.
 */
@Entity
@Table(name = "abtesting")
public class AbTestRoute {
    @Id
    @Column(name = "service_name", nullable = false)
    String serviceName;

    @Column(name = "active", nullable = false)
    private String active;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Column(name = "weight", nullable = false)
    private Integer weight;


    public String getEndpoint() {
        return endpoint;
    }

    public String getActive() {
        return active;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
