package com.test.api.democurrency.exception;

public class BitcoinPriceException extends RuntimeException {
    public BitcoinPriceException(String message) {
        super(message);
    }

    public BitcoinPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
