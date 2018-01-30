package com.coinbase.exchange.api.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NewOrderSingle {

    @JsonProperty("client_oid")
    private String clientOid; //optional
    private String type; //limit, market or stop
    private String side;

    @JsonProperty("product_id")
    private String productId;

    //SELF-TRADE PREVENTION
    //optional: values are dc, co , cn , cb
    private String stp;

    public NewOrderSingle() {
    }

    public NewOrderSingle(String clientOid, String type, String side, String productId, String stp) {
        this.clientOid = clientOid;
        this.type = type;
        this.side = side;
        this.productId = productId;
        this.stp = stp;
    }

    public String getStp() {
        return stp;
    }

    public void setStp(String stp) {
        this.stp = stp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getClientOid() {
        return clientOid;
    }

    public void setClientOid(String clientOid) {
        this.clientOid = clientOid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
