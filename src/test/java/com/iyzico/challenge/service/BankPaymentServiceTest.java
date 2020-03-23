package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class BankPaymentServiceTest {
    /**
     * this class will update payment where BANK_RESPONSE column is null
     */

    @Autowired
    private IyzicoPaymentService iyzicoPaymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void getBankResponseAndUpdatePayment_And_checkIsPaidToBank()throws InterruptedException {
        //iyzicoPaymentService.checkNotPaid();
        Thread.sleep(40000);
        List<Payment> fivePayment = paymentRepository.findPaymentsByBankResponseIsNotNull();//If bank returns code it means not null
        assertEquals(fivePayment.size(), 7);//there was already existed one record
    }


}
