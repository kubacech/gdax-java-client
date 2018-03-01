**This project is currently under heavy development and very probably not functional. 
If you want to use this, then please fork it and make it work for you as desired.**

# gdax-java-client

Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction). Originally forked from [irufus/gdax-java](https://github.com/irufus/gdax-java). 
This project aims to simple java library that is wrapping GDAX API. Using new Spring Webclient nonblocking APIs
    
# Usage

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
gdax.orderService().createOrder(newOrder);
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


# WebsocketFeed API 
//TODO rewritten completely . not tested at all at this moment
 
# Functions tested by this fork:
- [x] Authentication (GET, POST, DELETE supported)
- [x] Get Account
- [x] Get Accounts
- [x] Get Account History
- [ ] Get Holds
- [x] Place a new Order (limit order)
- [x] Get an Order
- [ ] Cancel an Order
- [x] List all open Orders
- [x] Get Market Data
- [x] List fills
- [x] List Products
- [ ] HTTP Error code support
- [x] List of Currencies
- [ ] Withdrawals - from coinbase accounts / payment methods / crypto account address
- [ ] Deposits - from coinbase accounts / payment methods
- [ ] Transfers - from coinbase accounts
- [ ] Payment methods - coinbase / payment methods
- [ ] Reports
- [ ] Pagination support for all calls that support it.
