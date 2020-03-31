package com.iyzico.challenge.service;

import com.iyzico.challenge.configuration.CustomResponseErrorHandler;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuyControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${endPoint}")
    private String endPoint;
    private String apiUrl = "/buy";

    public RestTemplate getRestTemplate() {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Before
    public void createEndPointUrl() {
        this.apiUrl = endPoint + apiUrl;
    }

    @Test
    @Transactional
    @Rollback(false)
    public void aAddProduct_id_1() {

        Product product = new Product();
        product.setId(1L);
        product.setStock(1);
        product.setPrice(new BigDecimal(2222222));
        product.setName("ELMA");
        product.setDescription("Buying test");

        ResponseEntity<String> forEntity = getRestTemplate().postForEntity(endPoint + "/product/add", product, String.class);
        Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(forEntity.getBody(), "Succesfully created");
    }

    @Test
    public void bBuyProduct() {
        ResponseEntity<String> forObject = getRestTemplate().getForEntity(apiUrl + "?id=1", String.class);
        Assert.assertEquals(forObject.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(forObject.getBody(), "SOLD");
    }

    @Test
    public void cOutOfStock() {
        ResponseEntity<String> forObject = getRestTemplate().getForEntity(apiUrl + "?id=1", String.class);
        Assert.assertEquals(forObject.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(forObject.getBody(), "OUT OF STOCK");
    }

    @Test
    public void dBuyInvalidProduct() {
        ResponseEntity<String> forObject = getRestTemplate().getForEntity(apiUrl + "?id=5", String.class);
        Assert.assertEquals(forObject.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(forObject.getBody(), "INVALID PRODUCT");
    }

    @Test
    public void eNOT_sending_Id_value() {

        ResponseEntity<String> forObject = getRestTemplate().getForEntity(apiUrl + "?id=", String.class);
        Assert.assertEquals(forObject.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void fBad_Request_Error_NOT_sending_parameter() {
        ResponseEntity<String> forObject = getRestTemplate().getForEntity(apiUrl, String.class);
        Assert.assertEquals(forObject.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
