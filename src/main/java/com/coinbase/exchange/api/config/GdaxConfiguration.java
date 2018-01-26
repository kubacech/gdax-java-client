package com.coinbase.exchange.api.config;

import com.coinbase.exchange.api.exchange.Signature;

public class GdaxConfiguration {

    private static final String DEFAULT_BASE_URL = "https://api.gdax.com/";
    private static final String DEFAULT_BASE_WS_URL = "wss://ws-feed.gdax.com/";

    private String baseUrl;
    private String websocketUrl;
    private String key;
    private String secret;
    private String passphrase;

    public GdaxConfiguration() {
        this.key = System.getenv("GDAX_KEY");
        this.secret = System.getenv("GDAX_SECRET");
        this.passphrase = System.getenv("GDAX_PASS");
    }

    public GdaxConfiguration(String key, String secret, String passphrase) {
        this.key = key;
        this.secret = secret;
        this.passphrase = passphrase;
        this.baseUrl = DEFAULT_BASE_URL;
        this.websocketUrl = DEFAULT_BASE_WS_URL;
    }

    public GdaxConfiguration(String key, String secret, String passphrase, String baseUrl, String websocketUrl) {
        this.key = key;
        this.secret = secret;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
        this.websocketUrl = websocketUrl;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String websocketUrl() {
        return websocketUrl;
    }

    public String key() {
        return key;
    }

    public String secret() {
        return secret;
    }

    public String passphrase() {
        return passphrase;
    }

    public Signature signature(){
        return new Signature(secret);
    }
}
