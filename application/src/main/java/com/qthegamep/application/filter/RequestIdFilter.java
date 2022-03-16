package com.qthegamep.application.filter;

import com.qthegamep.application.service.GenerationService;
import com.qthegamep.application.util.Constants;
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
@Order(20)
public class RequestIdFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestIdFilter.class);

    private final GenerationService generationService;

    @Autowired
    public RequestIdFilter(GenerationService generationService) {
        this.generationService = generationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestId = getRequestId(servletRequest);
        LOG.debug("RequestId: {}", requestId);
        servletRequest.setAttribute(Constants.REQUEST_ID_HEADER, requestId);
        ((HttpServletResponse) servletResponse).addHeader(Constants.REQUEST_ID_HEADER, requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getRequestId(ServletRequest servletRequest) {
        String requestIdHeader = ((HttpServletRequest) servletRequest).getHeader(Constants.REQUEST_ID_HEADER);
        if (requestIdHeader == null || requestIdHeader.isEmpty()) {
            String xRequestIdHeader = ((HttpServletRequest) servletRequest).getHeader(Constants.X_REQUEST_ID_HEADER);
            return xRequestIdHeader == null || xRequestIdHeader.isEmpty()
                    ? generationService.generateUniqueId(10L)
                    : xRequestIdHeader;
        } else {
            return requestIdHeader;
        }
    }
}
