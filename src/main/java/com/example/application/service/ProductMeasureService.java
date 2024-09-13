package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.model.product.ProductMeasure;
import com.example.application.repository.ProductMeasureRepository;

import java.util.List;

@Service
public class ProductMeasureService {

    private final ProductMeasureRepository productMeasureRepository;

    public ProductMeasureService(@Autowired ProductMeasureRepository productMeasureRepository) {
        this.productMeasureRepository = productMeasureRepository;
    }

    public List<ProductMeasure> findAll() {
        return productMeasureRepository.findAll();
    }
}
