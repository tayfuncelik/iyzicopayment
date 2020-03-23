package com.iyzico.challenge.controller;

import com.iyzico.challenge.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/buy")
public class BuyController {


    @Autowired
    private BuyService buyService;

    /**
     * Selected Product will be bought
     *
     * @param id
     * @return INVALID PRODUCT,OUT OF STOCK,SOLD
     */
    @GetMapping()
    private ResponseEntity<String> buyProduct(@RequestParam Long id) {

        String message = buyService.buyProduct(id, false);
        return ResponseEntity.ok(message);
    }
}
