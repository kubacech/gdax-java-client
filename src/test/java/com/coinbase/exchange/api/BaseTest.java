package com.coinbase.exchange.api;

import org.junit.Before;


public abstract class BaseTest {

    protected GdaxClient gdax;

    @Before
    public void baseinit() {
        this.gdax = new GdaxClient();
    }
}
