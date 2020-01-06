package com.zjb.zuulservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zjb.zuulservice.utils.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zjb on 2020/1/6.
 */
@Component
public class ResponseFilter extends ZuulFilter {

    @Autowired
    FilterUtils filterUtils;

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        //将token写到response的header中
        RequestContext ctx = RequestContext.getCurrentContext();
        LOGGER.debug("adding the CORRELATION_ID to the outbound headers.{}", ctx.getZuulRequestHeaders().get(FilterUtils.CORRELATION_ID));
        ctx.getResponse().addHeader(filterUtils.CORRELATION_ID, ctx.getZuulRequestHeaders().get(FilterUtils.CORRELATION_ID));
        LOGGER.debug("completing outgoing request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }
}
