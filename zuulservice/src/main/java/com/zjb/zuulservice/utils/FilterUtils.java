package com.zjb.zuulservice.utils;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Created by zjb on 2019/12/18.
 */
@Component
public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "pre";
    private static final String CORRELATION_ID = "zjb-correlation-id";

    public Object getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader("zjb-correlation-id") != null) {
            return ctx.getRequest().getHeader("zjb-correlation-id");
        }
        return ctx.getZuulRequestHeaders().get("zjb-correlation-id");
    }

    public void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
}
