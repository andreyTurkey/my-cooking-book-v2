package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.PhotoLink;

public interface PhotoLinkRepository extends JpaRepository<PhotoLink, Long> {

    void removeAllByRecipeId(Long recipeId);
}
