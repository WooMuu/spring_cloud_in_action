package com.zjb.ssosvr.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by zjb on 2019/12/25.
 * 这个类所实现的接口作用：
 * 注册到restTemplate中，在发送HTTP请求时拦截请求，调用intercept方法对request做一些处理。
 * 例如本例中，给header增加了用户token等信息
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.set(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.set(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        return execution.execute(request, body);
    }

}
