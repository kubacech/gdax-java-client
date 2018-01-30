package com.coinbase.exchange.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewLimitOrderSingle extends NewOrderSingle {

    private BigDecimal size;
    private BigDecimal price;

    //The post-only flag indicates that the order should only make liquidity.
    // If any part of the order results in taking liquidity, the order will be rejected and no part of it will execute.
    @JsonProperty("post_only")
    private Boolean postOnly;

    /**
     * GTC Good till canceled
     * GTT Good till time
     * IOC Immediate or cancel
     * FOK Fill or kill orders are rejected if the entire size cannot be matched.
     */
    //[optional] GTC, GTT, IOC, or FOK (default is GTC)
    @JsonProperty("time_in_force")
    private String timeInForce;

    //[optional]* min, hour, day ; time in force must be GTT
    @JsonProperty("cancel_after")
    private String cancelAfter;

    public NewLimitOrderSingle() {}

    public NewLimitOrderSingle(String clientOid, String side, String productId, String stp,
                               BigDecimal size, BigDecimal price, Boolean postOnly, String timeInForce, String cancelAfter) {
        super(clientOid, "limit", side, productId, stp);
        this.size = size;
        this.price = price;
        this.postOnly = postOnly;
        this.timeInForce = timeInForce;
        this.cancelAfter = cancelAfter;
    }

    public Boolean getPostOnly() {
        return postOnly;
    }

    public void setPostOnly(Boolean postOnly) {
        this.postOnly = postOnly;
    }

    public BigDecimal getPrice() {
        return price.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSize() {
        return size.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getCancelAfter() {
        return cancelAfter;
    }

    public void setCancelAfter(String cancelAfter) {
        this.cancelAfter = cancelAfter;
    }
}
