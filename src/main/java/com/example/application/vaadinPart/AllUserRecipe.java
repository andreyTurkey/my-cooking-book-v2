package com.example.application.vaadinPart;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.VaadinSecurity.UserLogin;
import com.example.application.dto.RecipeDto;
import com.example.application.mapper.RecipeMapper;
import com.example.application.repository.RecipeRepository;
import com.example.application.service.PhotoLinkService;
import com.example.application.service.ProductService;

import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Route("/allUserRecipe")
@RolesAllowed(value = "USER")
@Setter
@Slf4j
@UIScope
public class AllUserRecipe extends VerticalLayout {

    private final RecipeRepository recipeRepository;
    private final PhotoLinkService photoLinkService;
    private final ProductService productService;
    private Grid<RecipeDto> recipeGrid;
    private List<RecipeDto> recipeDtos;
    private static Div hint;

    public AllUserRecipe(@Autowired RecipeRepository recipeRepository,
                         @Autowired ProductService productService,
                         @Autowired PhotoLinkService photoLinkService) {
        this.recipeRepository = recipeRepository;
        this.productService = productService;
        this.photoLinkService = photoLinkService;

        recipeDtos = recipeRepository.findAllByUserLogin(UserLogin.getCurrentUserLogin())
                .stream()
                .map(RecipeMapper::getRecipeDtoProductEmpty)
                .collect(Collectors.toList());
        this.setupGrid();

        HorizontalLayout horizontalLayout = new HorizontalLayout(addNewRecipeButton(), returnToMainPage());
        add(horizontalLayout);
    }

    public void setupGrid() {
        recipeGrid = new Grid<>(RecipeDto.class, false);
        recipeGrid.setWidth("50%");
        recipeGrid.setAllRowsVisible(true);
        recipeGrid.addColumn(RecipeDto::getDateOfCreating).setHeader("Дата создания")
                .setWidth("10em").setFlexGrow(0);
        recipeGrid.addColumn(RecipeDto::getName).setHeader("Название").setAutoWidth(true);

        recipeGrid.addColumn(
                        new ComponentRenderer<>(Button::new, (button, recipeDto) -> {
                            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                    ButtonVariant.LUMO_ERROR,
                                    ButtonVariant.LUMO_TERTIARY);
                            button.addClickListener(e -> button.getUI()
                                    .ifPresent(ui -> ui.navigate(ChangeRecipePage.class, recipeDto.getId())));
                            button.setIcon(new Icon(VaadinIcon.CHECK));
                        })).setHeader("Изменить")
                .setWidth("7em").setFlexGrow(0);

        recipeGrid.addColumn(
                        new ComponentRenderer<>(Button::new, (button, removalRecipe) -> {
                            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                    ButtonVariant.LUMO_ERROR,
                                    ButtonVariant.LUMO_TERTIARY);
                            button.addClickListener(e -> {
                                ConfirmDialog dialog = new ConfirmDialog();
                                dialog.setHeader("Удалить рецепт?");
                                dialog.setText("Вы удалите рецепт без возможности восстановления");
                                dialog.setRejectable(true);
                                dialog.setRejectText("Отмена");
                                dialog.addRejectListener(event -> event.getSource().close());
                                dialog.setConfirmText("ОК");
                                log.debug("ТЕКУЩИЙ ID Рецепта " + removalRecipe.getId());
                                dialog.addConfirmListener(event -> {
                                    this.removeInvitation(removalRecipe);
                                });
                                dialog.open();
                            });
                            button.setIcon(new Icon(VaadinIcon.TRASH));
                        })).setHeader("Удалить")
                .setWidth("7em").setFlexGrow(0);

        recipeGrid.setItems(recipeDtos);

        hint = new Div();
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");

        add(hint, recipeGrid);
    }

    private void removeInvitation(RecipeDto recipeDto) {
        log.debug("Пришел в метод рецепт с ID = " + recipeDto);
        if (recipeDto == null)
            return;
        log.debug("Продукы для удаления: " + recipeDto.getProducts());
        productService.deleteProduct(recipeDto.getProducts());
        photoLinkService.deleteRecipePhotoLink(recipeDto.getId());
        recipeDtos.remove(recipeDto);
        recipeRepository.deleteById(recipeDto.getId());
        this.refreshGrid();
    }

    private void refreshGrid() {
        if (recipeDtos.size() > 0) {
            recipeGrid.setVisible(true);
            hint.setVisible(false);
            recipeGrid.getDataProvider().refreshAll();
        } else {
            recipeGrid.setVisible(false);
            hint.setVisible(true);
        }
    }

    private Button addNewRecipeButton() {
        Button addNewRecipe = new Button("Добавить новый рецепт");
        addNewRecipe.addClickListener(e -> addNewRecipe.getUI()
                .ifPresent(ui -> ui.navigate(NewRecipeForm.class)));
        addNewRecipe.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        return addNewRecipe;
    }

    private Button returnToMainPage() {
        Button returnToMainPage = new Button("На главную страницу");
        returnToMainPage.addClickListener(e -> returnToMainPage.getUI()
                .ifPresent(ui -> ui.navigate(WorkSpacePage.class)));
        return returnToMainPage;
    }
}
