package com.zjb.zuulservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zjb on 2019/12/10.
 */
@Component
public class ServiceConfig {
    @Value("${signning.key}")
    private String JwtSigningKey;

    public String getJwtSigningKey() {
        return JwtSigningKey;
    }
}
