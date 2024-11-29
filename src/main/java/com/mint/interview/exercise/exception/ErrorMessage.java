package com.mint.interview.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Integer code;
    private String error;
    private String details;
}
