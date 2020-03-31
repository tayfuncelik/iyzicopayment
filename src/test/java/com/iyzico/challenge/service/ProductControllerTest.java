package com.iyzico.challenge.service;

import com.iyzico.challenge.configuration.CustomResponseErrorHandler;
import com.iyzico.challenge.dto.ProductReqDto;
import com.iyzico.challenge.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    @Value("${endPoint}")
    private String endPoint;
    private String apiUrl = "/product";

    @Before
    public void createEndPointUrl() {
        this.apiUrl = endPoint + apiUrl;
    }

    @Autowired
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Test
    @Transactional
    @Rollback(false)
    public void aGetProduct_id_1() {

        ResponseEntity<Product> forEntity = getRestTemplate().getForEntity(apiUrl + "/getById?id=1",Product.class);
        Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void bAddProduct_id_1() {

        Product product = new Product();
        product.setId(1L);
        product.setStock(1);
        product.setPrice(new BigDecimal(2222222));
        product.setName("ELMA");
        product.setDescription("test case");

        ResponseEntity<String> forEntity = getRestTemplate().postForEntity(apiUrl + "/add", product, String.class);
        Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(forEntity.getBody(), "Succesfully created");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void cUpdate_product_MissingProductId_id_is_null() {

        ProductReqDto product = new ProductReqDto();
        product.setStock(2);
        product.setDescription("stock updated product 1");

        ResponseEntity<String> forEntity = getRestTemplate().postForEntity(apiUrl + "/update", product, String.class);
        Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(forEntity.getBody(), "PRODUCT ID MISSING");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void dUpdate_product_Not_found_Product_by_product_id_6() {

        ProductReqDto product = new ProductReqDto();
        product.setId(6L);
        product.setStock(2);
        product.setDescription("stock updated product 6");

        ResponseEntity<String> forEntity = getRestTemplate().postForEntity(apiUrl + "/update", product, String.class);
        Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(forEntity.getBody(), "PRODUCT IS NOT EXIST");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void eUpdateProduct_id_1() {

        ResponseEntity<Product> forEntity = getRestTemplate().getForEntity(apiUrl + "/getById?id=1",Product.class);

        if( forEntity.getStatusCode().is2xxSuccessful()){
            ProductReqDto product = new ProductReqDto();
            product.setId(1L);
            product.setName("Armut");
            product.setStock(2);
            product.setPrice(new BigDecimal(5555555));
            product.setDescription("test case");

            ResponseEntity<String> resEntity = getRestTemplate().postForEntity(apiUrl + "/update", product, String.class);
            Assert.assertEquals(resEntity.getStatusCode(), HttpStatus.OK);
            Assert.assertEquals(resEntity.getBody(), "Succesfully updated");
        }

    }

    @Test
    @Transactional
    @Rollback(false)
    public void fRemoveProduc_id_1t() {

        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        getRestTemplate().delete(apiUrl + "/delete/{id}", 1);
    }

}
