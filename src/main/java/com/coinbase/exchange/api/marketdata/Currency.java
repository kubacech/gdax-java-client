package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Currency {

    @JsonProperty("id")
    private String code;

    private String name;

    @JsonProperty("min_size")
    private BigDecimal minSize;

    public Currency() {
    }

    public Currency(String code, String name, BigDecimal minSize) {
        this.code = code;
        this.name = name;
        this.minSize = minSize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinSize() {
        return minSize;
    }

    public void setMinSize(BigDecimal minSize) {
        this.minSize = minSize;
    }
}
