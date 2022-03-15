package com.qthegamep.spring.boot.example.filter;

import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.ServletRequest;

@ControllerAdvice
public class DurationResponseFilter implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(DurationResponseFilter.class);

    private final ServletRequest servletRequest;

    @Autowired
    public DurationResponseFilter(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String requestId = (String) servletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String startTime = (String) servletRequest.getAttribute(Constants.START_TIME_HEADER);
        String duration = String.valueOf(System.currentTimeMillis() - Long.parseLong(startTime));
        LOG.debug("Duration: {} RequestId: {}", duration, requestId);
        response.getHeaders().add(Constants.DURATION_HEADER, duration);
        return body;
    }
}
