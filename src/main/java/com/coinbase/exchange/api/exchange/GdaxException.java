package com.coinbase.exchange.api.exchange;

public class GdaxException extends RuntimeException {

    private int httpStatus;

    public GdaxException(int httpStatus) {
        this(httpStatus, "");
    }

    public GdaxException(int httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public GdaxException(int httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public GdaxException(int httpStatus, Throwable cause) {
        this(httpStatus, "", cause);
    }


}
