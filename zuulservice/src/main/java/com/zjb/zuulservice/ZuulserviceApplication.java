package com.zjb.zuulservice;

import com.zjb.specialroutes.utils.UserContextFilter;
import com.zjb.specialroutes.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy //使服务成为一个Zuul服务器
public class ZuulserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulserviceApplication.class, args);
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
