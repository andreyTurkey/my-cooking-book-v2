package com.example.application.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.application.model.product.Product;
import com.example.application.repository.ProductRepository;

import java.util.List;

@Component
public class ProductService  {

    private final ProductRepository productRepository;

    public ProductService(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(List<Product> products) {
        productRepository.deleteAll(products);
    }
}
