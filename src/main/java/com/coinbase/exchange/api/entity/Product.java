package com.coinbase.exchange.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Product {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("base_min_size")
    private BigDecimal baseMinSize;

    @JsonProperty("base_max_size")
    private BigDecimal baseMaxSize;

    @JsonProperty("quote_increment")
    private BigDecimal quoteIncrement;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public BigDecimal getBaseMinSize() {
        return baseMinSize;
    }

    public void setBaseMinSize(BigDecimal baseMinSize) {
        this.baseMinSize = baseMinSize;
    }

    public BigDecimal getBaseMaxSize() {
        return baseMaxSize;
    }

    public void setBaseMaxSize(BigDecimal baseMaxSize) {
        this.baseMaxSize = baseMaxSize;
    }

    public BigDecimal getQuoteIncrement() {
        return quoteIncrement;
    }

    public void setQuoteIncrement(BigDecimal quoteIncrement) {
        this.quoteIncrement = quoteIncrement;
    }
}
