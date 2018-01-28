package com.coinbase.exchange.api.MarketData;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class MarketDataTest extends BaseTest {

    MarketDataService marketDataService;

    @Before
    public void init() {
        this.marketDataService = gdax.marketDataService();
    }

    @Test
    public void canGetMarketDataForLevelOneBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "1").block();
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }

    @Test
    public void canGetMarketDataForLevelTwoBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "2").block();
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }

    /**
     * note that the returned results are slightly different for level 3. For level 3 you will see an
     * order Id rather than the count of orders at a certain price.
     */
    @Test
    public void canGetMarketDataForLevelThreeBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "3").block();
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }
}
