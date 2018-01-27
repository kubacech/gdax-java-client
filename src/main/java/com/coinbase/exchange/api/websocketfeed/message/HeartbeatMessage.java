package com.coinbase.exchange.api.websocketfeed.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class HeartbeatMessage extends Message {

    private Long sequence;

    @JsonProperty("last_trade_id")
    protected Long lastTradeId;

    @JsonProperty("last_trade_id")
    private String productId;

    private Instant time;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getLastTradeId() {
        return lastTradeId;
    }

    public void setLastTradeId(Long lastTradeId) {
        this.lastTradeId = lastTradeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
