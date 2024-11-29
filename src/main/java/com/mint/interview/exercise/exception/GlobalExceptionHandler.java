package com.mint.interview.exercise.exception;

import com.mint.interview.exercise.constants.CommonMessages;
import com.mint.interview.exercise.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageUtils messageUtil;

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Throwable ex) {
        log.error(ex.getMessage(), ex);
        return createErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, CommonMessages.INTERNAL_ERROR);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        return createErrorMessage(ex.getHttpStatus(), ex.getCode(), ex.getArgs());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private ResponseEntity<ErrorMessage> createErrorMessage(HttpStatus httpStatus, String errorCode, Object... params) {
        String message = messageUtil.getMessage(errorCode, params);
        ErrorMessage errorMessage = new ErrorMessage(httpStatus.value(), errorCode, message);
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
    
}
