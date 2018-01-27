package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;


public class UserAccountService {

    static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";

    private GdaxExchange exchange;

    public UserAccountService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    public Mono<UserAccountData> getUserAccounts(){
        return exchange.get(USER_ACCOUNT_ENDPOINT, new ParameterizedTypeReference<UserAccountData>() {});
    }
}
