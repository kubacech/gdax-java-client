package com.coinbase.exchange.api.websocketfeed.message;

public abstract class Message {

    protected String type;

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }
}
