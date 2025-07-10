package com.example.datajpa.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Locale;
@Builder
public record ErrorResponse <T>(
        String message,
        Integer code,
        LocalDateTime timestamp,
        T details
) {
}
