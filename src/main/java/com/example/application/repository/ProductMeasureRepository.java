package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.product.ProductMeasure;

public interface ProductMeasureRepository extends JpaRepository<ProductMeasure, Integer> {
}
