package com.zjb.zuulservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zjb.zuulservice.config.ServiceConfig;
import com.zjb.zuulservice.utils.FilterUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by zjb on 2019/12/18.
 */
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;

    private static final boolean SHOULD_FILTER = true;

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private ServiceConfig serviceConfig;


    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        if (filterUtils.getCorrelationId() != null) {
            return true;
        }
        return false;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object run() throws ZuulException {
        if (isCorrelationIdPresent()) {
            logger.debug("zjb-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("zjb-correlation-id generate in tracking filter:{}.", filterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
        System.out.println(getOrganizationId());
        return null;
    }

    private String getOrganizationId() {
        String result = "";
        if (filterUtils.getAuthToken() != null) {
            String authToken = filterUtils.getAuthToken().replace("Bearer", "");

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(serviceConfig.getJwtSigningKey().getBytes())
                        .parseClaimsJws(authToken)
                        .getBody();
                result = (String) claims.get("organizationId");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
