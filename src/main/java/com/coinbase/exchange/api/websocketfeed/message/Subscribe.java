package com.coinbase.exchange.api.websocketfeed.message;

// Used for signing the subscribe message to the Websocket feed
public class Subscribe extends Message {

    String[] product_ids;
    String[] channels;

    public Subscribe() { }

    public Subscribe(String[] product_ids, String[] channels) {
        this.type = "subscribe";
        this.product_ids = product_ids;
        this.channels = channels;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String[] product_ids) {
        this.product_ids = product_ids;
    }

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }
}
