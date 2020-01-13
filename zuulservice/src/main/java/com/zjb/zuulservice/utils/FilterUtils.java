package com.zjb.zuulservice.utils;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zjb on 2019/12/18.
 */
@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "zjb-correlation-id";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "zjb-user-id";
    public static final String ORG_ID = "zjb-org-id";

    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(CORRELATION_ID) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        }
        Map<String, String> headers = ctx.getZuulRequestHeaders();
        String s = headers.get(CORRELATION_ID);
        return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
    }

    public void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public String getServiceId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("serviceId") == null) return "";
        //We might not have a service id if we are using a static, non-eureka route.
        return ctx.get("serviceId").toString();
    }

    public String getAuthToken() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(AUTH_TOKEN) != null) {
            return ctx.getRequest().getHeader(AUTH_TOKEN);
        }
        Map<String, String> headers = ctx.getZuulRequestHeaders();
        String s = headers.get(AUTH_TOKEN);
        return ctx.getZuulRequestHeaders().get(AUTH_TOKEN);
    }
}
