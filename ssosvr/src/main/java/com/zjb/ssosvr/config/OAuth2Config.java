package com.zjb.ssosvr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * Created by zjb on 2020/1/10.
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService detailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("EagleEye") //提供应用程序（EagleEye）的名称
                .secret(passwordEncoder.encode("EagleEye"))
                .authorizedGrantTypes("refresh_token", //授权批准类型的列表，会在你的 OAuth2 服务支持列表
                        "password",
                        "client_credentials")
                .scopes("webclient", "mobileclient"); //定义当他们询问你的 OAuth2 服务器访问令牌时，调用的应用程序可以运行的边界

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(detailsService);
    }
}
