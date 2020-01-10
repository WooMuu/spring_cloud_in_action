package com.zjb.organization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by zjb on 2020/1/10.
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //任何具有ADMIN角色的用户访问/orgnizations开头的DELETE方法时都将被方形
                .antMatchers(HttpMethod.DELETE, "/organizations/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
