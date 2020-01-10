package com.zjb.zuulservice.model;

/**
 * Created by zjb on 2020/1/8.
 */
public class AbTestRoute {
    private String endpoint;
    private String active;
    private int weight;
    String serviceName;

    public String getEndpoint() {
        return endpoint;
    }

    public String getActive() {
        return active;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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
