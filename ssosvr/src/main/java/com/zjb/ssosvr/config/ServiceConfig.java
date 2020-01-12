package com.zjb.ssosvr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by zjb on 2020/1/12.
 */
@Component
@Configuration
public class ServiceConfig {

    @Value("${signning.key}")
    private String JwtSigningKey;

    public String getJwtSigningKey() {
        return JwtSigningKey;
    }

}
