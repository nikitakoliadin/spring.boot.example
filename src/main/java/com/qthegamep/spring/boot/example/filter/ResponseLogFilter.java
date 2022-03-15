package com.qthegamep.spring.boot.example.filter;

import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(10)
public class ResponseLogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseLogFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        String path = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        String requestId = (String) servletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String clientIp = servletRequest.getRemoteAddr();
        String duration = ((HttpServletResponse) servletResponse).getHeader(Constants.DURATION_HEADER);
        LOG.info("Request processed. Path: {} RequestId: {} Client IP: {} Duration: {}", path, requestId, clientIp, duration);
    }
}
