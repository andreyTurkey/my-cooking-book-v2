package com.example.application.vaadinPart;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.example.application.service.PhotoLinkService;
import com.example.application.service.ProductMeasureService;
import com.example.application.service.ProductNameService;
import com.example.application.service.RecipeService;

import jakarta.annotation.security.RolesAllowed;

@Route("/newRecipe")
@RolesAllowed(value = "USER")
@Setter
@Slf4j
@UIScope
public class NewRecipeForm extends RecipeForm  {
    public NewRecipeForm(ProductNameService productNameService,
                         ProductMeasureService productMeasureService,
                         RecipeService recipeService,
                         PhotoLinkService photoLinkService,
                         UploadMemoryBuffer uploadMemoryBuffer) {
        super(productNameService,
                productMeasureService,
                recipeService,
                photoLinkService,
                uploadMemoryBuffer);
    }
}
