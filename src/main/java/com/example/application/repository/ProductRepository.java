package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
