package com.zjb.ssosvr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by zjb on 2020/1/10.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    @Bean //②Spring Security 使用 AuthenticationManagerBean 来处理身份验证。
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean //Spring Security 使 用 UserDetailsService 来 处 理 Spring Security 返回的用户信息。
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new JdbcUserDetailService();
    }

    //configure() 方法将定义用户、密码和角色。
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(myPasswordEncoder()) //srping5之后，必须给auth加上个密码加密器
//                .withUser("user")
//                .password(myPasswordEncoder().encode("user"))
//                .roles("USER")
//                .and()
//                .withUser("root")
//                .password(myPasswordEncoder().encode("root"))
//                .roles("USER", "ADMIN");

        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**", "/css/**", "/images/**")
                .and()
                .ignoring()
                .antMatchers("/user/**");
    }

}
