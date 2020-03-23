package com.iyzico.challenge.service;

import com.iyzico.challenge.configuration.CustomResponseErrorHandler;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;


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
    public void aBuyProduct() {
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
