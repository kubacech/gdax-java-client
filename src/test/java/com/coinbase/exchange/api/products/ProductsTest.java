package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class ProductsTest extends BaseTest {

    ProductService productService;

    @Before
    public void init() {
        this.productService = gdax.productService();
    }

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts().block();
        assertTrue(products.size() >= 0);
    }
}
