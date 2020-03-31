package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private BankService bankService;
    private PaymentRepository paymentRepository;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }
/*
    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);
        //insert records
        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);
        paymentRepository.save(payment);
        logger.info("Payment saved successfully!");
    }
*/

    /**
     * Insert all payment requests to DB
     *
     * @param price
     */
    public void pay(BigDecimal price) {

        Payment payment = new Payment();
        payment.setPrice(price);
        paymentRepository.save(payment);
        logger.info("Payment saved successfully!");
    }

    public void payToBankAndBankResp(BigDecimal price, Long paymentId) {
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse res = bankService.pay(request);
        if (paymentId == null) {
            Payment payment = new Payment();
            payment.setPrice(price);
            payment.setBankResponse(res.getResultCode());
            paymentRepository.save(payment);
        } else {
            paymentRepository.updatePayment(res.getResultCode(), paymentId);
        }
    }

    /**
     * time out 30second
     * each bank request cost 5 second
     * We can only update 6 record each time
     */
    @Scheduled(fixedRate = 30000)
    public void checkNotPaid() {
        logger.info("Bank payment process started {}", dateFormat.format(new Date()));

        List<Payment> sixPayment = paymentRepository.findPaymentsByBankResponseIsNull(PageRequest.of(0, 6));
        //List<BigDecimal> priceList = sixPayment.stream().map(Payment::getPrice).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(sixPayment)) {

            logger.info("these payments will be payed by bank");
            sixPayment.forEach(payment -> {
                payToBankAndBankResp(payment.getPrice(), payment.getId());
            });
        }

        logger.info("Bank payment process completed at {}", dateFormat.format(new Date()));
    }
}
