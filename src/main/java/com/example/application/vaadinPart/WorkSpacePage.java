package com.example.application.vaadinPart;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.VaadinSecurity.SecurityService;

import jakarta.annotation.security.RolesAllowed;

@Route("/workSpace")
@Setter
@Slf4j
@RolesAllowed(value = "USER")
@UIScope
public class WorkSpacePage extends VerticalLayout  {

    @Autowired
    private SecurityService securityService;

    public WorkSpacePage() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        createButtons();
    }

    private void createButtons() {
        Button newRecipe = new Button("Новый рецепт", new Icon(VaadinIcon.NOTEBOOK));
        newRecipe.setClassName("button");
        newRecipe.addClickListener(e -> newRecipe.getUI()
                .ifPresent(ui -> ui.navigate(NewRecipeForm.class)));

        Button myRecipes = new Button("Мои рецепты", new Icon(VaadinIcon.RECORDS));
        myRecipes.setClassName("button");
        myRecipes.addClickListener(e -> myRecipes.getUI()
                .ifPresent(ui -> ui.navigate(AllUserRecipe.class)));

        Button buyProduct = new Button("Купить продукты", new Icon(VaadinIcon.CART));
        buyProduct.setClassName("button");

        Button logout = new Button("Выйти", new Icon(VaadinIcon.CART));
        logout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        logout.addClickListener(e -> securityService.logout());

        VerticalLayout verticalLayout = new VerticalLayout(newRecipe, myRecipes, buyProduct, logout);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }
}
