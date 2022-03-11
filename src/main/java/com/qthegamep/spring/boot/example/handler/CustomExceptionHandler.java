package com.qthegamep.spring.boot.example.handler;

import com.qthegamep.spring.boot.example.dto.ErrorResponseDTO;
import com.qthegamep.spring.boot.example.exception.ServiceException;
import com.qthegamep.spring.boot.example.model.ErrorType;
import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception, HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURL().toString();
        String requestId = (String) httpServletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        String clientIp = httpServletRequest.getRemoteAddr();
        String startTime = (String) httpServletRequest.getAttribute(Constants.START_TIME_HEADER);
        String duration = String.valueOf(System.currentTimeMillis() - Long.parseLong(startTime));
        ErrorType errorType = getErrorType(exception);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorCode(errorType.getErrorCode());
        LOG.error("Error. Path: {} RequestId: {} Client IP: {} Duration: {} Error response DTO: {}", path, requestId, clientIp, duration, errorResponseDTO, exception);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(Constants.DURATION_HEADER, duration);
        return new ResponseEntity<>(errorResponseDTO, headers, HttpStatus.BAD_REQUEST);
    }

    private ErrorType getErrorType(Exception exception) {
        if (exception instanceof ServiceException) {
            return ((ServiceException) exception).getErrorType();
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }
}
