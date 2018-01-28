package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.NewLimitOrderSingle;
import com.coinbase.exchange.api.entity.NewMarketOrderSingle;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import com.coinbase.exchange.api.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class OrderTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(OrderTests.class);

    ProductService productService;
    AccountService accountService;
    MarketDataService marketDataService;
    OrderService orderService;

    @Before
    public void init() {
        this.productService = gdax.productService();
        this.accountService = gdax.accountService();
        this.marketDataService = gdax.marketDataService();
        this.orderService = gdax.orderService();
    }

    // accounts: BTC, USD, GBP, EUR, CAD
    // products: BTC-USD, BTC-GBP, BTC-EUR, ETH-BTC, ETH-USD, LTC-BTC, LTC-USD

    /**
     * Not Strictly the best test but tests placing the order and
     * then cancelling it without leaving a mess.
     * Note: You'll need credit available
     */
    @Test
    public void canMakeLimitOrderAndGetTheOrderAndCancelIt() {
        List<Account> accounts = accountService.getAccounts().block();
        Optional<Account> accountsWithMoreThanZeroCoinsAvailable = accounts.stream()
                .filter(account -> account.getBalance().compareTo(BigDecimal.ONE) > 0)
                .findFirst();

        assertTrue(accountsWithMoreThanZeroCoinsAvailable.isPresent());

        String productId;
        if (accountsWithMoreThanZeroCoinsAvailable.get().equals("BTC")) {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-USD";
        } else {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-BTC";
        }

        MarketData marketData = getMarketDataOrderBook(productId);

        assertTrue(marketData != null);

        BigDecimal price = getAskPrice(marketData).setScale(8, BigDecimal.ROUND_HALF_UP);
        BigDecimal size = new BigDecimal("0.01").setScale(8, BigDecimal.ROUND_HALF_UP);

        NewLimitOrderSingle limitOrder = getNewLimitOrderSingle(productId, price, size);

        Order order = orderService.createOrder(limitOrder).block();

        assertTrue(order != null);
        assertEquals(productId, order.getProduct_id());
        assertEquals(size, new BigDecimal(order.getSize()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals(price, new BigDecimal(order.getPrice()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals("limit", order.getType());

        orderService.cancelOrder(order.getId());
        List<Order> orders = orderService.getOpenOrders().block();
        orders.stream().forEach(o -> assertTrue(o.getId() != order.getId()));
    }

    @Test
    public void cancelAllOrders() {
        List<Order> cancelledOrders = orderService.cancelAllOpenOrders().block();
        assertTrue(cancelledOrders.size() >=0);
    }

    @Test
    public void getAllOpenOrders() {
        List<Order> openOrders = orderService.getOpenOrders().block();
        assertTrue(openOrders.size() >= 0);
    }

    @Test
    public void getFills() {
        List<Fill> fills = orderService.getAllFills().block();
        assertTrue(fills.size() >= 0);
    }

    @Test
    public void createMarketOrderBuy(){
        NewMarketOrderSingle marketOrder = createNewMarketOrder("BTC-USD", "buy", new BigDecimal(0.01));
        Order order = orderService.createOrder(marketOrder).block();

        assertTrue(order != null); //make sure we created an order
        String orderId = order.getId();
        assertTrue(orderId.length() > 0); //ensure we have an actual orderId
        Order filledOrder = orderService.getOrder(orderId).block();
        assertTrue(filledOrder != null); //ensure our order hit the system
        assertTrue(new BigDecimal(filledOrder.getSize()).compareTo(BigDecimal.ZERO) > 0); //ensure we got a fill
        log.info("Order opened and filled: " + filledOrder.getSize() + " @ " + filledOrder.getExecuted_value()
             + " at the cost of " + filledOrder.getFill_fees());
   }
   @Test
   public void createMarketOrderSell(){
       NewMarketOrderSingle marketOrder = createNewMarketOrder("BTC-USD", "sell", new BigDecimal(0.01));
       Order order = orderService.createOrder(marketOrder).block();
       assertTrue(order != null); //make sure we created an order
       String orderId = order.getId();
       assertTrue(orderId.length() > 0); //ensure we have an actual orderId
       Order filledOrder = orderService.getOrder(orderId).block();
       assertTrue(filledOrder != null); //ensure our order hit the system
       assertTrue(new BigDecimal(filledOrder.getSize()).compareTo(BigDecimal.ZERO) > 0); //ensure we got a fill
       log.info("Order opened and filled: " + filledOrder.getSize() + " @ " + filledOrder.getExecuted_value()
               + " at the cost of " + filledOrder.getFill_fees());
   }
    private NewMarketOrderSingle createNewMarketOrder(String product, String action, BigDecimal size){
        NewMarketOrderSingle marketOrder = new NewMarketOrderSingle();
        marketOrder.setProduct_id(product);
        marketOrder.setSide(action);
        marketOrder.setSize(size);
        return marketOrder;
    }

    private MarketData getMarketDataOrderBook(String product) {
        return marketDataService.getMarketDataOrderBook(product, "1").block();
    }

    private NewLimitOrderSingle getNewLimitOrderSingle(String productId, BigDecimal price, BigDecimal size) {
        NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
        limitOrder.setProduct_id(productId);
        if (productId.contains("-BTC")) {
            limitOrder.setSide("sell");
        } else {
            limitOrder.setSide("buy");
        }
        limitOrder.setType("limit");
        limitOrder.setPrice(price);
        limitOrder.setSize(size);
        return limitOrder;
    }

    private BigDecimal getAskPrice(MarketData marketData) {
        return marketData.getAsks().get(0).getPrice().setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
