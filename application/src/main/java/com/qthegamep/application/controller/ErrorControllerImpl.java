package com.qthegamep.application.controller;

import com.qthegamep.application.util.Paths;
import com.qthegamep.application.util.Templates;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping(
            path = Paths.ERROR_PATH,
            produces = {MimeTypeUtils.TEXT_HTML_VALUE}
    )
    public String handleError(HttpServletRequest httpServletRequest) {
        Integer status = (Integer) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            if (status == HttpStatus.NOT_FOUND.value()) {
                return Templates.ERROR_404_TEMPLATE;
            }
        }
        return Templates.ERROR_TEMPLATE;
    }
}
