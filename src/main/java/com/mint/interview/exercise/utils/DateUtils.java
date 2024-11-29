package com.mint.interview.exercise.utils;

import com.mint.interview.exercise.domain.Event;

import java.time.LocalDateTime;

public class DateUtils {
    public static boolean isOverlapping(LocalDateTime startDateX, LocalDateTime endDateX,
                                  LocalDateTime startDateY, LocalDateTime endDateY) {
        return startDateX.isBefore(endDateY) &&
                endDateX.isAfter(startDateY);
    }
}
