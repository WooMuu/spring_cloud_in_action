package com.zjb.ssosvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  //用于discoveryClient模式的服务发现；当时用支持Ribbon的resttemplate时可以删除
@EnableFeignClients  //许可服务中采用feign客户端
//@EnableResourceServer  //必须加上这个注解才能解析token
@EnableAuthorizationServer
public class SsosvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsosvrApplication.class, args);
	}

	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


}
