package com.qthegamep.spring.boot.example.filter;

import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(30)
public class DurationRequestFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(DurationRequestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestId = (String) servletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String startTime = String.valueOf(System.currentTimeMillis());
        LOG.debug("StartTime: {} RequestId: {}", startTime, requestId);
        servletRequest.setAttribute(Constants.START_TIME_HEADER, startTime);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
