package com.irrah.back_end.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionMessage {
    private int error;
    private HttpStatus status;
    private String message;
}
