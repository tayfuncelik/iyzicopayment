package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.repository.PaymentRepository;
import net.bytebuddy.asm.Advice;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuyServiceTest {

    @Autowired
    private BuyService buyService;

    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentRepository paymentRepository;


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
    @Transactional
    @Rollback(false)
    public void checkBoughtItemPaid() {

        buyService.buyProduct(2L, false);
        Product p = productService.findProductById(2L);
        Payment payment = paymentRepository.findPaymentsByPriceIs(p.getPrice());

        assertEquals(payment.getBankResponse(), "200");
    }
}
