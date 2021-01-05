package com.epam.esm.handler;

import lombok.Value;

@Value
public class ErrorHandler {
    String errorMessage;
    int errorCode;
}
