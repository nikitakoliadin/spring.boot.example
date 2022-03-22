package com.qthegamep.application.filter;

import com.qthegamep.application.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(10)
public class ResponseLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        String path = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        String requestId = (String) servletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String clientIp = servletRequest.getRemoteAddr();
        String duration = ((HttpServletResponse) servletResponse).getHeader(Constants.DURATION_HEADER);
        if (duration == null) {
            String startTime = (String) servletRequest.getAttribute(Constants.START_TIME_HEADER);
            duration = String.valueOf(System.currentTimeMillis() - Long.parseLong(startTime));
        }
        log.info("Request processed. Path: {} Client IP: {} Duration: {} RequestId: {}", path, clientIp, duration, requestId);
    }
}
