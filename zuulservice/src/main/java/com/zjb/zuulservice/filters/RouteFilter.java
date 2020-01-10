package com.zjb.zuulservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zjb.zuulservice.model.AbTestRoute;
import com.zjb.zuulservice.utils.FilterUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by zjb on 2020/1/8.
 */
@Component
public class RouteFilter extends ZuulFilter {

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    private ProxyRequestHelper helper = new ProxyRequestHelper();


    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        AbTestRoute abTestRoute = getAbRoutingInfo(filterUtils.getServiceId());
        if (abTestRoute != null && useSpecialRoute(abTestRoute)) {
            String route = buildRouteString(ctx.getRequest().getRequestURI(), abTestRoute.getEndpoint(), ctx.get("serviceId").toString());
            forwardToSpecialRoute(route);
        }
        return null;
    }


    private AbTestRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestRoute> abTestRoute = null;
        try {
            abTestRoute = restTemplate.exchange(
                    "http://specialroutesservice/route/abtesting/{serviceName}",
                    HttpMethod.GET,
                    null,
                    AbTestRoute.class,
                    serviceName);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) return null;
            throw e;
        }
        return abTestRoute.getBody();
    }

    private boolean useSpecialRoute(AbTestRoute testRoute) {
        Random random = new Random();
        if (testRoute.getActive().equals("N")) return false;//检查路由是否有效
        int value = random.nextInt((10 - 1) + 1) + 1;//确定是否使用替代的服务路由
        if (testRoute.getWeight() < value)
            return true;
        return false;
    }

    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
        //此处用serviceName截取URL的方式前提是zuul的服务路由配置中，转发服务的路由中第一个必须是serviceId，
        //例如 specialroutesservice: /specialroutesservice/**
        int index = oldEndpoint.indexOf(serviceName);
        String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        System.out.println("Target route: " + String.format("%s/%s", newEndpoint, strippedRoute));

        return String.format("%s/%s", newEndpoint, strippedRoute);
    }

    private void forwardToSpecialRoute(String route) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
        MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
        String verb = getVerb(request);
        InputStream requestEntity = getRequestBody(request);
        if (request.getContentLength() < 0) {
            ctx.setChunkedRequestBody();
        }

        this.helper.addIgnoredHeaders();

        CloseableHttpClient httpClient = null;
        HttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();
            response = forward(
                    httpClient,
                    verb,
                    route,
                    request,
                    headers,
                    params,
                    requestEntity);

            setResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setResponse(HttpResponse response) throws IOException {
        this.helper.setResponse(response.getStatusLine().getStatusCode(),
                response.getEntity() == null ? null : response.getEntity().getContent(),
                revertHeaders(response.getAllHeaders()));
    }

    private MultiValueMap<String, String> revertHeaders(Header[] allHeaders) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (Header header : allHeaders) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<String>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
    }

    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        } catch (IOException e) {
//            no reqeustBody is ok

        }
        return requestEntity;
    }

    private String getVerb(HttpServletRequest request) {
        String method = request.getMethod();
        return method.toUpperCase();
    }

    private HttpResponse forward(
            HttpClient httpClient, String verb, String uri, HttpServletRequest request,
            MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity) throws IOException {
        Map<String, Object> info = this.helper.debug(verb, uri, headers, params, requestEntity);

        URL host = new URL(uri);
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        int contentLength = request.getContentLength();

        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength, request.getContentType() != null ? ContentType.create(request.getContentType()) : null);
        switch (verb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(uri);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(uri);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(uri);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, uri);
        }

        httpRequest.setHeaders(convertHeaders(headers));
        HttpResponse zuulResponse = forwardRequest(httpClient, httpHost, httpRequest);
        return zuulResponse;
    }

    private HttpHost getHttpHost(URL host) {
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
        return httpHost;
    }

    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        ArrayList<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        return list.toArray(new BasicHeader[0]);
    }

    private HttpResponse forwardRequest(HttpClient httpClient, HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        HttpResponse response = httpClient.execute(httpHost, httpRequest);
        return response;
    }


}
