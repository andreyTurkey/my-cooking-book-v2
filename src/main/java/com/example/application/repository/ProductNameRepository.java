package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.product.ProductName;

public interface ProductNameRepository extends JpaRepository<ProductName, Integer>{
}
