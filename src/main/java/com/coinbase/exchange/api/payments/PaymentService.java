package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentService {

    static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    private GdaxExchange exchange;

    public PaymentService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    public PaymentTypes getPaymentTypes() {
        return exchange.get(PAYMENT_METHODS_ENDPOINT, new ParameterizedTypeReference<PaymentTypes>(){});
    }

    public CoinbaseAccounts getCoinbaseAccounts() {
        return exchange.get(COINBASE_ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<CoinbaseAccounts>() {});
    }
}