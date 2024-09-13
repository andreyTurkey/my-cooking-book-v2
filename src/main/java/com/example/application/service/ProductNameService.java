package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.model.product.ProductName;
import com.example.application.repository.ProductNameRepository;

import java.util.List;

@Service
public class ProductNameService {

    private final ProductNameRepository productNameRepository;

    public ProductNameService(@Autowired ProductNameRepository productNameRepository) {
        this.productNameRepository = productNameRepository;
    }

    public List<ProductName> findAll() {
        return productNameRepository.findAll();
    }
}
