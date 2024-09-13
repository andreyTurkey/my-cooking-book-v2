package com.example.application.vaadinPart;

import com.example.application.service.PhotoLinkService;
import com.example.application.service.ProductMeasureService;
import com.example.application.service.ProductNameService;
import com.example.application.service.RecipeService;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/changeRecipe")
@Setter
@Slf4j
@RolesAllowed(value = "USER")
public class ChangeRecipePage extends RecipeForm implements HasUrlParameter<Long> {

    private Long recipeDtoId;

    public ChangeRecipePage(@Autowired ProductNameService productNameService,
                            @Autowired ProductMeasureService productMeasureService,
                            @Autowired RecipeService recipeService,
                            @Autowired PhotoLinkService photoLinkService,
                            @Autowired UploadMemoryBuffer uploadMemoryBuffer) {
        super(productNameService, productMeasureService, recipeService, photoLinkService, uploadMemoryBuffer);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long o) {
        recipeDtoId = o;
        printParameter();
    }

    private void printParameter() {
        log.debug("Параметр - " + recipeDtoId);
    }
}
