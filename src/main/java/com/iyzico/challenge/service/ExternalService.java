package com.iyzico.challenge.service;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${iyzipay.setApiKey}")
    private String setApiKey;

    @Value("${iyzipay.setSecretKey}")
    private String setSecretKey;

    @Value("${iyzipay.iyzipayUrl}")
    private String iyzipayUrl;

    public String pay() {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId("123456789");
        request.setPrice(new BigDecimal("1"));
        request.setPaidPrice(new BigDecimal("1.2"));
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setPaymentCard(getPaymentCard());
        request.setBuyer(getBuyer());
        request.setShippingAddress(getShippingAddress());
        request.setBillingAddress(getShippingAddress());//BILLING ADDRESS IS SAME ADDRESS

        List<BasketItem> basketItems = new ArrayList<BasketItem>();//ITEMS CAN ME MORE THAN ONE IN BASKET
        basketItems.add(getItem());
        request.setBasketItems(basketItems);//ALL PRICE SHOULD BE EQUAL TO REQUEST PRICE

        Payment payment = Payment.create(request, getOptions());
        logger.info("IYZIPAY external payment result: {}", payment);
        return payment.getErrorMessage();
    }

    private Options getOptions() {
        Options options = new Options();
        options.setApiKey(setApiKey);
        options.setSecretKey(setSecretKey);
        options.setBaseUrl(iyzipayUrl);
        return options;
    }

    private BasketItem getItem() {
        BasketItem basketItem = new BasketItem();
        basketItem.setId("BI101");
        basketItem.setName("Binocular");
        basketItem.setCategory1("Collectibles");
        basketItem.setCategory2("Accessories");
        basketItem.setItemType(BasketItemType.PHYSICAL.name());
        basketItem.setPrice(new BigDecimal("1"));
        return basketItem;
    }

    private PaymentCard getPaymentCard() {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        return paymentCard;
    }

    private Address getShippingAddress() {
        Address shippingAddress = new Address();
        shippingAddress.setContactName("TAYFUN CELIK");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("KAGITHANE");
        shippingAddress.setZipCode("34742");
        return shippingAddress;
    }

    private Buyer getBuyer() {
        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("TAYFUN");
        buyer.setSurname("CELIK");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("KAGITHANE");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        return buyer;
    }
}
