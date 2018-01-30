package com.coinbase.exchange.api.exchange;

public class GdaxErrorResponse {

    private String message;

    public GdaxErrorResponse() {
    }

    public GdaxErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
