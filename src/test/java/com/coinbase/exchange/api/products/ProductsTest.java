package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
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
