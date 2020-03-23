package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.ProductConvertor;
import com.iyzico.challenge.dto.ProductReqDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.service.BuyService;
import com.iyzico.challenge.service.PaymentServiceClients;
import com.iyzico.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConvertor productConvertor;

    @GetMapping("/getAll")
    private ResponseEntity<List<Product>> getProducts() {

        List<Product> allProduct = productService.findAllProducts();

        if (!CollectionUtils.isEmpty(allProduct)) {
            return ResponseEntity.ok(allProduct);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById")
    private ResponseEntity<Product> getProductById(@RequestParam(required = false) Long id) {

        Product product = productService.findProductById(id);

        if (product == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/update")
    private ResponseEntity<String> updateProduct(@RequestBody ProductReqDto productReqDto) {

        if (productReqDto == null || productReqDto.getId() == null) {
            return ResponseEntity.badRequest().body("PRODUCT ID MISSING");
        }
        Product product = productService.findProductById(productReqDto.getId());
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PRODUCT IS NOT EXIST");
        }

        Product convertedProduct = productConvertor.convertDtoToProduct(productReqDto);
        productService.updateProduct(convertedProduct);
        return ResponseEntity.ok("Succesfully updated");
    }

    @PostMapping("/add")
    private ResponseEntity<String> addProduct(@Valid @RequestBody(required = true) ProductReqDto productReqDto) {

        if (productReqDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Product product = productConvertor.convertDtoToProduct(productReqDto);
        if (product.getId() != null) {
            productService.addProductNative(product);
        } else {
            productService.addProduct(product);
        }

        return ResponseEntity.ok("Succesfully created");
    }

    @DeleteMapping("/delete/{id}")
    private void removeProduct(@PathVariable Long id) {

        productService.deleteProductById(id);
    }

}
