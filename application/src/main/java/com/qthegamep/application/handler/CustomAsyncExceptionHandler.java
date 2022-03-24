package com.qthegamep.application.handler;

import com.qthegamep.application.dto.ErrorResponseDTO;
import com.qthegamep.application.entity.Error;
import com.qthegamep.application.exception.ServiceException;
import com.qthegamep.application.mapper.ErrorMapper;
import com.qthegamep.application.model.ErrorType;
import com.qthegamep.application.repository.ErrorMongoRepository;
import com.qthegamep.application.repository.ErrorSQLRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final ErrorMongoRepository errorMongoRepository;
    private final ErrorSQLRepository errorSQLRepository;

    @Autowired
    public CustomAsyncExceptionHandler(ErrorMongoRepository errorMongoRepository,
                                       ErrorSQLRepository errorSQLRepository) {
        this.errorMongoRepository = errorMongoRepository;
        this.errorSQLRepository = errorSQLRepository;
    }

    @Override
    public void handleUncaughtException(Throwable exception, Method method, Object... params) {
        String requestId = (String) params[0];
        ErrorType errorType = getErrorType(exception);
        Error error = new Error();
        error.setRequestId(requestId);
        error.setErrorCode(errorType.getErrorCode());
        errorMongoRepository.save(error)
                .subscribe(result -> {
                    log.info("Async error has been saved to MongoDB {} RequestId: {}", result, requestId);
                    error.setId(null);
                });
        errorSQLRepository.save(error)
                .subscribe(result -> {
                    log.info("Async error has been saved to MySQL {} RequestId: {}", result, requestId);
                    error.setId(null);
                });
        ErrorResponseDTO errorResponseDTO = ErrorMapper.INSTANCE.errorToErrorResponseDTO(error);
        log.error("Error. Error response DTO: {} RequestId: {} ", errorResponseDTO, requestId, exception);
    }

    private ErrorType getErrorType(Throwable exception) {
        if (exception instanceof ServiceException) {
            return ((ServiceException) exception).getErrorType();
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }
}
