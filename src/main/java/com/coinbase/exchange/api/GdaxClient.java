package com.coinbase.exchange.api;

import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.config.GdaxConfiguration;
import com.coinbase.exchange.api.deposits.DepositService;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.coinbase.exchange.api.exchange.GdaxExchangeImpl;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.orders.OrderService;
import com.coinbase.exchange.api.payments.PaymentService;
import com.coinbase.exchange.api.products.ProductService;
import com.coinbase.exchange.api.reports.ReportService;
import com.coinbase.exchange.api.transfers.TransferService;
import com.coinbase.exchange.api.useraccount.UserAccountService;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.api.withdrawals.WithdrawalsService;

public class GdaxClient {

    protected GdaxConfiguration config;
    protected GdaxExchange exchange;

    private AccountService accountService;
    private DepositService depositService;
    private MarketDataService marketDataService;
    private OrderService orderService;
    private PaymentService paymentService;
    private ProductService productService;
    private ReportService reportService;
    private TransferService transferService;
    private UserAccountService userAccountService;
    private WithdrawalsService withdrawalsService;
    private WebsocketFeed websocketFeed;

    public GdaxClient() {
        this(new GdaxConfiguration());
    }

    public GdaxClient(String key, String secret, String passphrase) {
        this(new GdaxConfiguration(key, secret, passphrase));
    }

    public GdaxClient(GdaxConfiguration config) {
        this.config = config;
        this.exchange = new GdaxExchangeImpl(config.key(), config.passphrase(), config.baseUrl(), config.signature());
        createServices();
    }

    private void createServices() {
        this.accountService =  new AccountService(exchange);
        this.depositService =  new DepositService(exchange);
        this.marketDataService =  new MarketDataService(exchange);
        this.orderService =  new OrderService(exchange);
        this.paymentService =  new PaymentService(exchange);
        this.productService =  new ProductService(exchange);
        this.reportService =  new ReportService(exchange);
        this.transferService =  new TransferService(exchange);
        this.userAccountService =  new UserAccountService(exchange);
        this.withdrawalsService =  new WithdrawalsService(exchange);
        this.websocketFeed = new WebsocketFeed(config);
    }

    public AccountService accountService() {
        return accountService;
    }

    public DepositService depositService() {
        return depositService;
    }

    public MarketDataService marketDataService() {
        return marketDataService;
    }

    public OrderService orderService() {
        return orderService;
    }

    public PaymentService paymentService() {
        return paymentService;
    }

    public ProductService productService() {
        return productService;
    }

    public ReportService reportService() {
        return reportService;
    }

    public TransferService transferService() {
        return transferService;
    }

    public UserAccountService userAccountService() {
        return userAccountService;
    }

    public WithdrawalsService withdrawalsService() {
        return withdrawalsService;
    }

    public WebsocketFeed websocketFeed() {
        return websocketFeed;
    }
}
