package com.example.application.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "product_measure", schema = "public")
public class ProductMeasure {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_measure")
    private String name;
}
