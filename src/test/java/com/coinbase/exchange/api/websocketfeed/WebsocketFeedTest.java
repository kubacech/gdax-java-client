package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.config.GdaxConfiguration;
import com.coinbase.exchange.api.test.IntegrationTest;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertNotNull;

@Category(IntegrationTest.class)
public class WebsocketFeedTest {

    private WebsocketFeed feed;

    @Before
    public void init() {
        this.feed = new WebsocketFeed(gdaxConfig());
    }

    @Test
    public void subscribeTicker() {
        boolean gotMessage = false;
        feed.subscribe( new Subscribe(new String[] {"BTCEUR"}, new String[] {"ticker"})).subscribe(message -> {
           assertNotNull(message);
        });
        await().atMost(5, TimeUnit.SECONDS).until(() -> gotMessage);
    }


    private GdaxConfiguration gdaxConfig() {
        return new GdaxConfiguration("",
                "",
                "",
                "",
                "wss://ws-feed.gdax.com"
        );
    }
}