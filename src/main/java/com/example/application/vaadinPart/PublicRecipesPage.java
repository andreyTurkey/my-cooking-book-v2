package com.example.application.vaadinPart;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.dto.RecipeDto;
import com.example.application.repository.RecipeRepository;

import java.util.List;

@Route("/publicRecipe")
@AnonymousAllowed
@Setter
@Slf4j
@VaadinSessionScope
public class PublicRecipesPage extends VerticalLayout {

    private final RecipeRepository recipeRepository;
    private Grid<RecipeDto> recipeGrid;
    private List<RecipeDto> recipeDtos;
    private static Div hint;

    public PublicRecipesPage(@Autowired RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
}
