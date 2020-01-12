package com.zjb.ssosvr.security;

import com.zjb.ssosvr.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by zjb on 2020/1/12.
 * baeldung.com(http://www.baeldung.com/spring-security-oauth-jwt)介绍JWT的公钥和私钥使用
 */
@Configuration
public class JWTTokenStoreConfig {
    @Autowired
    private ServiceConfig serviceConfig;

    @Bean
    @Primary //Primary注解用来告诉spring，如果一个以上特定类型（如DefaultTokenServices）的bean，
    //使用标记为@Primary的bean进行自动注入
    public DefaultTokenServices defaultTokenServices() {//用于读取提交给服务的令牌的数据
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //定义了令牌将如何被转换
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {//充当JWT到OAuth2服务器之间的转换器
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置用亍签名的签名密钥
        converter.setSigningKey(serviceConfig.getJwtSigningKey());
        return converter;
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new JWTTokenEnhancer();
    }
}
