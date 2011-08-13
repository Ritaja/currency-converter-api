package com.tunyk.currencyconverter.api;

public class CurrencyNotSupportedException extends CurrencyConverterException {

    public CurrencyNotSupportedException() {
        super();
    }

    public CurrencyNotSupportedException(String message) {
        super(message);
    }

    public CurrencyNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotSupportedException(Throwable cause) {
        super(cause);
    }
}
