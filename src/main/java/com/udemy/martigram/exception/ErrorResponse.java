package com.udemy.martigram.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Date timestamp;
}
