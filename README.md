# gdax-java-client

Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction) that follows the development style similar to [coinbase-java](https://github.com/coinbase/coinbase-java). Originally forked from [irufus/gdax-java](https://github.com/irufus/gdax-java). 
This project aims to simple java library that is wrapping GDAX API without any Spring or UI
    
# Usage
--------

### Create GdaxClient
To access all API methods firstly create `GdaxClient` instance. This needs three parameters from you: 
**key**, **secret**, **passphrase**. You can pass this in constructor:

```java
new GdaxClient(key, secret, passphrase);
```

Or setup environment properties **GDAX_KEY**, **GDAX_SECRET**, **GDAX_PASS** and use default constructor.

### Using services
with created client object you can now access all services as this:

```java
client.orderService().createOrder(neworder);
```
##### List of services:
-  AccountService
-  DepositService
-  MarketDataService
-  OrderService
-  PaymentService
-  ProductService
-  ReportService
-  TransferService
-  UserAccountService
-  WithdrawalsService

# API
--------

The Api for this application/library is as follows:
(Note: this section is likely to change but is provided on the basis it will work well for example usage)

- `AccountService.getAccounts()` - returns a List Accounts
- `AccountService.getAccountHistory(String accountId)` - returns the history for a given account as a List
- `AccountService.getHolds(String accountId)` - returns a List of all held funds for a given account.
- `DepositService.depositViaPaymentMethod(BigDecimal amount, String currency, String paymentMethodId)` - makes a deposit from a stored payment method into your GDAX account
- `DepositService.coinbaseDeposit(BigDecimal amount, String currency, String coinbaseAccountId)` - makes a deposit from a coinbase account into your GDAX account
- `MarketDataService.getMarketDataOrderBook(String productId, String level)` - a call to ProductService.getProducts() will return the order book for a given product. You can then use the WebsocketFeed api to keep your orderbook up to date. This is implemented in this codebase. Level can be 1 (top bid/ask only), 2 (top 50 bids/asks only), 3 (entire order book - takes a while to pull the data.)
- `OrderService.getOpenOrders(String accountId)` - returns a List of Orders for any outstanding orders
- `OrderService.cancelOrder(String orderId)` - cancels a given order
- `OrderService.createOrder(NewOrderSingle aSingleOrder)` - construct an order and send it to this method to place an order for a given product on the exchange.
- `PaymentService.getCoinbaseAccounts()` - gets the coinbase accounts for the logged in user
- `PaymentService.getPaymentTypes()` - gets the payment types available for the logged in user
- `ProductService.getProducts()` - returns a List of Products available from the exchange - BTC-USD, BTC-EUR, BTC-GBP, etc.
- `ReportService.createReport(String product, String startDate, String endDate)` - not certain about this one as I've not tried it but presumably generates a report of a given product's trade history for the dates supplied


# WebsocketFeed API 
---------------------
//TODO
 
# Functions supported:
- [x] Authentication (GET, POST, DELETE supported)
- [x] Get Account
- [x] Get Accounts
- [x] Get Account History
- [x] Get Holds
- [x] Place a new Order (limit order)
- [x] Get an Order
- [x] Cancel an Order
- [x] List all open Orders
- [x] Get Market Data
- [x] List fills
- [x] List Products
- [x] HTTP Error code support
- [x] List of Currencies - from Accounts
- [x] Withdrawals - from coinbase accounts / payment methods / crypto account address
- [x] Deposits - from coinbase accounts / payment methods
- [x] Transfers - from coinbase accounts
- [x] Payment methods - coinbase / payment methods
- [x] Reports
- [x] Pagination support for all calls that support it.
- [x] Pagination support for all calls that support it.

# Functions tested by this fork:
- [ ] Authentication (GET, POST, DELETE supported)
- [ ] Get Account
- [ ] Get Accounts
- [ ] Get Account History
- [ ] Get Holds
- [ ] Place a new Order (limit order)
- [ ] Get an Order
- [ ] Cancel an Order
- [ ] List all open Orders
- [ ] Get Market Data
- [ ] List fills
- [ ] List Products
- [ ] HTTP Error code support
- [ ] List of Currencies - from Accounts
- [ ] Withdrawals - from coinbase accounts / payment methods / crypto account address
- [ ] Deposits - from coinbase accounts / payment methods
- [ ] Transfers - from coinbase accounts
- [ ] Payment methods - coinbase / payment methods
- [ ] Reports
- [ ] Pagination support for all calls that support it.
- [ ] Pagination support for all calls that support it.