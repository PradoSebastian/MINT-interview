package com.mint.interview.exercise.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    @Autowired
    private MessageSource msgSource;

    public String getMessage(String key) {
        return getMessage(key, "");
    }

    public String getMessage(String key, Object... params) {
        List<String> paramStrs = new ArrayList<>();
        if (Objects.nonNull(params)) {
            for (Object param : params) {
                String value = String.valueOf(param);
                if (param instanceof DefaultMessageSourceResolvable) {
                    value = ((DefaultMessageSourceResolvable) param).getDefaultMessage();
                }
                paramStrs.add(value);
            }
        }
        return msgSource.getMessage(key, paramStrs.toArray(), LocaleContextHolder.getLocale());
    }

    public String getMessage(FieldError fieldError) {
        return msgSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    }
}
