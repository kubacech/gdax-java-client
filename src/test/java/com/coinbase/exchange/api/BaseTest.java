package com.coinbase.exchange.api;

import org.junit.Before;

/**
 * Created by robevansuk on 20/01/2017.
 */
public abstract class BaseTest {

    protected GdaxClient client;

    @Before
    public void init() {
        this.client = new GdaxClient();
    }
}
