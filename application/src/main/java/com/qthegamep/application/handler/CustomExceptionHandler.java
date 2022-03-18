package com.qthegamep.application.handler;

import com.qthegamep.application.dto.ErrorResponseDTO;
import com.qthegamep.application.exception.ServiceException;
import com.qthegamep.application.model.ErrorType;
import com.qthegamep.application.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception, HttpServletRequest httpServletRequest) {
        String requestId = (String) httpServletRequest.getAttribute(Constants.REQUEST_ID_HEADER);
        ErrorType errorType = getErrorType(exception);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorCode(errorType.getErrorCode());
        log.error("Error. Error response DTO: {} RequestId: {} ", errorResponseDTO, requestId, exception);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    private ErrorType getErrorType(Exception exception) {
        if (exception instanceof ServiceException) {
            return ((ServiceException) exception).getErrorType();
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }
}
