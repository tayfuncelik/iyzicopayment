package com.iyzico.challenge.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProductServiceTest.class,
        BuyServiceTest.class,
        IyzicoPaymentServiceTest.class,
        BankPaymentServiceTest.class,
        // TODO INTEGRATION TEST This part need to be run table create
        //ProductControllerTest.class,
        //BuyControllerTest.class

})
public class AllTests {
}
