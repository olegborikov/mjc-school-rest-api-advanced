package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorHandler {
    private String errorMessage;
    private int errorCode;
}
