package com.zjb.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by zjb on 2019/12/10.
 */
//@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty;

    public String getExampleProperty() {
        return exampleProperty;
    }
}
