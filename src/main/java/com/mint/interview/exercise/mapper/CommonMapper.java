package com.mint.interview.exercise.mapper;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CommonMapper {
    public <X> X unwrap(Optional<X> optional) {
        return Objects.nonNull(optional) ? optional.orElse(null) : null;
    }

    public <X> Optional<X> wrap(X element) {
        return Optional.ofNullable(element);
    }
}
