package com.iyzico.challenge.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        ProductServiceTest.class,
        BuyServiceTest.class,
        IyzicoPaymentServiceTest.class,
        BankPaymentServiceTest.class,
        //INTEGRATION TEST
        ProductControllerTest.class,
        BuyControllerTest.class

})
public class AllTests {
}
