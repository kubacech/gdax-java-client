package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class OrderService {

    private GdaxExchange exchange;

    public static final String ORDERS_ENDPOINT = "/orders";

    public OrderService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    public Mono<List<Hold>> getHolds(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/holds", new ParameterizedTypeReference<Hold[]>(){});
    }

    public Mono<List<Order>> getOpenOrders(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/orders", new ParameterizedTypeReference<Order[]>(){});
    }

    public Mono<Order> getOrder(String orderId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + orderId,new ParameterizedTypeReference<Order>(){});
    }

    public Mono<Order> createOrder(NewOrderSingle order) {
        return exchange.post(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order>(){}, order);
    }

    public Mono<String> cancelOrder(String orderId) {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        return exchange.delete(deleteEndpoint, new ParameterizedTypeReference<String>(){});
    }

    public Mono<List<Order>> getOpenOrders() {
        return exchange.getAsList(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){});
    }

    public Mono<List<Order>> cancelAllOpenOrders() {
        return exchange.delete(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){})
                .map(Arrays::asList);
    }

    public Mono<List<Fill>> getAllFills() {
        String fillsEndpoint = "/fills";
        return exchange.getAsList(fillsEndpoint, new ParameterizedTypeReference<Fill[]>(){});
    }

    public Mono<List<Fill>> getFills(Long tradeIdFrom) {
        String fillsEndpoint = "/fills";
        if (tradeIdFrom != null && tradeIdFrom > 0) {
            fillsEndpoint += "?before=" + tradeIdFrom;
        }
        return exchange.getAsList(fillsEndpoint, new ParameterizedTypeReference<Fill[]>(){});
    }
}


