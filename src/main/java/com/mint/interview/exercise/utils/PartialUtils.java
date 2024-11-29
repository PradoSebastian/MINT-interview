package com.mint.interview.exercise.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartialUtils {
    public static boolean validateOptionalPresent(Optional optional) {
        return Objects.nonNull(optional) && optional.isPresent();
    }
}
