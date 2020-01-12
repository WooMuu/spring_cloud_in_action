package com.zjb.ssosvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
//@EnableResourceServer  //必须加上这个注解才能解析token
@EnableAuthorizationServer
public class SsosvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsosvrApplication.class, args);
	}



}
