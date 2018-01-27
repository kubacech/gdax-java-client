package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class ProductService {

    private GdaxExchange exchange;

    public static final String PRODUCTS_ENDPOINT = "/products";

    public ProductService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    // no paged products necessary
    public Mono<List<Product>> getProducts() {
        return exchange.getAsList(PRODUCTS_ENDPOINT, new ParameterizedTypeReference<Product[]>(){});
    }
}
