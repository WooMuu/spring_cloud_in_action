package com.zjb.license;

import com.zjb.utils.UserContextFilter;
import com.zjb.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableDiscoveryClient  //用于discoveryClient模式的服务发现；当时用支持Ribbon的resttemplate时可以删除
@EnableFeignClients  //许可服务中采用feign客户端
@EnableCircuitBreaker
public class LicensingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    @Bean
    public Filter userContextfilter() {
        return new UserContextFilter();
    }

    @Bean
    @LoadBalanced  //表明restTemplate使用ribbon
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (interceptors == null) {
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
    }

}
