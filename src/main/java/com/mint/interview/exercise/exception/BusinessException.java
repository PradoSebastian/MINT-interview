package com.mint.interview.exercise.exception;

import com.mint.interview.exercise.utils.MessageUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;
    private String code;
    private Object[] args;

    public BusinessException(String code, Object[] args) {
        super(code.concat(" ").concat(Arrays.toString(args)));
        this.code = code;
        this.args = args;
    }

    public BusinessException(HttpStatus httpStatus, String code, Object... args) {
        super(httpStatus.toString().concat(code.concat(" ").concat(Arrays.toString(args))));
        this.httpStatus = httpStatus;
        this.code = code;
        this.args = args;
    }

    public BusinessException(Throwable cause, HttpStatus httpStatus, String code, Object[] args) {
        super(cause);
        this.httpStatus = httpStatus;
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code) {
        super(code);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorMessage getErrorMessage(MessageUtils messageUtils) {
        String message = messageUtils.getMessage(this.code, this.args);
        return new ErrorMessage(httpStatus.value(), this.code, message);
    }
}
