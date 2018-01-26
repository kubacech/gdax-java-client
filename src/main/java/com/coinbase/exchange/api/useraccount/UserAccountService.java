package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 17/02/2017.
 */
public class UserAccountService {

    static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";

    private GdaxExchange exchange;

    public UserAccountService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    public UserAccountData getUserAccounts(){
        return exchange.get(USER_ACCOUNT_ENDPOINT, new ParameterizedTypeReference<UserAccountData>() {});
    }
}
