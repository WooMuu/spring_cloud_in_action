package com.zjb.ssosvr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zjb on 2019/12/19.
 */
@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UserContextHolder.getContext().builder()
                .setCorrelationId(request.getHeader(UserContext.CORRELATION_ID))
                .setAuthToken(request.getHeader(UserContext.AUTH_TOKEN))
                .setUserId(request.getHeader(UserContext.USER_ID))
                .setOrgId(request.getHeader(UserContext.ORG_ID));
        filterChain.doFilter(request, servletResponse);
    }

}
