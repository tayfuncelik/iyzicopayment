package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.repository.ProductRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void dGetAllProduct() {
        List<Product> p = productService.findAllProducts();
        assertEquals(p.size(), 2);
    }

    @Test
    public void bFindProductById() {
        Product product = productService.findProductById(2L);
        assertEquals(product.getId(), Long.valueOf(2));
    }

    @Test
    public void aAddProduct() {

        Product product = new Product();
        product.setStock(1);
        product.setPrice(new BigDecimal(2222222));
        product.setName("ELMA");
        product.setDescription("test case");

        productService.addProduct(product);
        Product p = productService.findProductById(product.getId());
        assertEquals(p.getId(), product.getId());
    }

    @Test
    public void addProductNative() {

        Product product = new Product();
        product.setId(2L);
        product.setStock(1);
        product.setPrice(new BigDecimal(2222222));
        product.setName("ELMA");
        product.setDescription("test case");

        productService.addProductNative(product);
        Product p = productService.findProductById(product.getId());
        assertEquals(p.getId(), product.getId());
    }



    @Test
    public void cUpdateProduct() {

        Product product = new Product();
        product.setId(2L);
        product.setStock(2);
        product.setPrice(new BigDecimal(666));
        product.setName("armut");
        product.setDescription("changed");

        productService.updateProduct(product);
        Product updatedProduct = productService.findProductById(product.getId());
        assertEquals(updatedProduct.getId(), product.getId());
        assertEquals(updatedProduct.getStock(), product.getStock());
        assertEquals(updatedProduct.getName(), product.getName());
        assertEquals(updatedProduct.getDescription(), product.getDescription());

    }

    @Test
    public void eDeleteProductById() {
        List<Product> p = productService.findAllProducts();
        p.forEach(item->{
            if(!item.getId().equals(2L)){
                productService.deleteProductById(item.getId());
            }
        });
    }

}
