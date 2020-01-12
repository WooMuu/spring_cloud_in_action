package com.zjb.ssosvr.security;

import com.zjb.ssosvr.model.UserOrganization;
import com.zjb.ssosvr.repository.OrgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * Created by zjb on 2020/1/12.
 */
public class JWTTokenEnhancer implements TokenEnhancer {

    @Autowired
    private OrgUserRepository orgUserRepository;

    private String getOrgId(String userName) {
        UserOrganization orgUser = orgUserRepository.findByUserName(userName);
        return orgUser.getOrganizationId();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        HashMap<String, Object> additionInfo = new HashMap<>();
        String orgId = getOrgId(oAuth2Authentication.getName());
        additionInfo.put("organizationId", orgId);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionInfo);
        return oAuth2AccessToken;
    }
}
