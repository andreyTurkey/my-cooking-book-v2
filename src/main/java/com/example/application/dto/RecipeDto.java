package com.example.application.dto;

import lombok.Builder;
import lombok.Data;
import com.example.application.model.product.Product;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RecipeDto {

    private Long id;
    private String name;
    private String description;
    private List<Product> products;
    private String duration;
    private Boolean isPublic;
    private LocalDate dateOfCreating;
    private String userLogin;
    private Boolean isModerated;
}

