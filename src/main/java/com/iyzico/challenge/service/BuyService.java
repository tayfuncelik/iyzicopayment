package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BuyService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private IyzicoPaymentService iyzicoPaymentService;

    @Autowired
    private ExternalService externalService;

    public synchronized String buyProduct(Long id, Boolean enableExternalPayment) {

        logger.info("will buy with Id {}", id);
        Product product = productService.findProductById(id);
        if (product == null) {
            return "INVALID PRODUCT";
        }

        if (product.getStock() != null && product.getStock() == 0) {//OUT OF STOCK
            return "OUT OF STOCK";
        } else {//IN STOCK AND WILL REDUCE

            if (enableExternalPayment) {
                String errorMessage = externalService.pay();
                if (errorMessage != null) {
                    return errorMessage;
                }
                return "ERROR";
            } else {
                //iyzicoPaymentService.pay(product.getPrice());
                iyzicoPaymentService.payToBankAndBankResp(product.getPrice(),null);

                //paid and now stock will reduce
                product.setStock(product.getStock() - 1);
                productService.updateProduct(product);
                return "SOLD";
            }

        }
    }
}
