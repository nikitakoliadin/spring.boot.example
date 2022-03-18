package com.qthegamep.application.filter;

import com.qthegamep.application.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
@Order(30)
public class DurationRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestId = (String) servletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String startTime = String.valueOf(System.currentTimeMillis());
        log.debug("StartTime: {} RequestId: {}", startTime, requestId);
        servletRequest.setAttribute(Constants.START_TIME_HEADER, startTime);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
