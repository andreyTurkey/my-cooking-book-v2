package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.product.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByUserLogin(String userLogin);
}
