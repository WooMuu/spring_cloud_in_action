package com.zjb.zuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy //使服务成为一个Zuul服务器
public class ZuulserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulserviceApplication.class, args);
    }

}
