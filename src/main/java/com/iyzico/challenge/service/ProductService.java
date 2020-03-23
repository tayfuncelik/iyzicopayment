package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
        logger.info("Product created successfully!");
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product.getId(), product.getName(), product.getDescription(), product.getStock(), product.getPrice());
        logger.info("Product updated successfully by id {} !", product.getId());
    }

    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
            logger.info("Product deleted with given id {}!", id);
        } catch (Exception e) {
            logger.error("Product id {} delete ERROR {}", id, e.getMessage());
        }
    }

    public void addProductNative(Product product) {
        productRepository.addProductNative(product.getId(), product.getName(), product.getStock(), product.getPrice(), product.getDescription());
        logger.info("Product created successfully with native query!");
    }
}
