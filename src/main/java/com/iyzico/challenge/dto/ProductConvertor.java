package com.iyzico.challenge.dto;

import com.iyzico.challenge.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductConvertor {

    public Product convertDtoToProduct(ProductReqDto productReqDto) {
        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setDescription(productReqDto.getDescription());
        product.setPrice(productReqDto.getPrice());
        product.setStock(productReqDto.getStock());
        product.setId(productReqDto.getId());
        return product;
    }

    public ProductRespDto convertProductToDto(Product product) {
        ProductRespDto productRespDto = new ProductRespDto();
        productRespDto.setName(product.getName());
        productRespDto.setDescription(product.getDescription());
        productRespDto.setPrice(product.getPrice());
        productRespDto.setStock(product.getStock());
        //productRespDto.setId(product.getId());
        return productRespDto;
    }
}
