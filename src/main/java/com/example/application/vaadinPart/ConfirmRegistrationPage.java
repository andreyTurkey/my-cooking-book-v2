package com.example.application.vaadinPart;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.VaadinSecurity.UserLogin;
import com.example.application.model.User;
import com.example.application.service.UserService;

import jakarta.annotation.security.RolesAllowed;

@Route("/confirmRegistration")
@Setter
@Slf4j
@RolesAllowed(value = "USER")
@VaadinSessionScope
public class ConfirmRegistrationPage extends VerticalLayout {

    public ConfirmRegistrationPage(@Autowired UserService userService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        String userLogin = UserLogin.getCurrentUserLogin();
        User user = userService.getUserByName(userLogin);

        NativeLabel line1 = new NativeLabel("Спасибо за регистрацию!");
        NativeLabel line2 = new NativeLabel("Учетные данные для входа были направлены");
        NativeLabel line3 = new NativeLabel(String.format("на указанную почту %s", user.getEmail()));

        add(line1);
        add(line2);
        add(line3);

        Button goOn = new Button("Продолжить");
        goOn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        goOn.addClickListener(e -> goOn.getUI().ifPresent(ui -> ui.navigate(WorkSpacePage.class)));
        add(goOn);
    }
}
