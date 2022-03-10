package com.qthegamep.spring.boot.example.filter;

import com.qthegamep.spring.boot.example.service.GenerationService;
import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(10)
public class RequestIdRequestFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestIdRequestFilter.class);

    private final GenerationService generationService;

    @Autowired
    public RequestIdRequestFilter(GenerationService generationService) {
        this.generationService = generationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestIdHeader = ((HttpServletRequest) servletRequest).getHeader(Constants.REQUEST_ID_HEADER);
        if (requestIdHeader == null || requestIdHeader.isEmpty()) {
            String xRequestIdHeader = ((HttpServletRequest) servletRequest).getHeader(Constants.X_REQUEST_ID_HEADER);
            String requestId = xRequestIdHeader == null || xRequestIdHeader.isEmpty()
                    ? generationService.generateUniqueId(10L)
                    : xRequestIdHeader;
            LOG.debug("RequestId: {}", requestId);
            servletRequest.setAttribute(Constants.REQUEST_ID_HEADER, requestId);
            ((HttpServletResponse) servletResponse).addHeader(Constants.REQUEST_ID_HEADER, requestId);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOG.debug("RequestId header: {}", requestIdHeader);
        }
    }
}
