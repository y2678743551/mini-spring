package org.springframework.core.convert;

public class ConverterException extends RuntimeException {
    public ConverterException(String message) {
        super(message);
    }
    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
