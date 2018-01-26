package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by robevansuk on 08/02/2017.
 */
public class ProductsTest extends BaseTest {

    ProductService productService;

    @Before
    public void init() {
        this.productService = client.productService();
    }

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts();
        assertTrue(products.size() >= 0);
    }
}
