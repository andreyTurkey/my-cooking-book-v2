package com.example.application.vaadinPart;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.VaadinSecurity.SecurityService;
import com.example.application.VaadinSecurity.UserLogin;
import com.example.application.dto.UserDto;
import com.example.application.exception.ReadPropertiesException;
import com.example.application.mail.RegistrationMail;
import com.example.application.model.Authority;
import com.example.application.service.AuthorityService;
import com.example.application.service.UserService;

import jakarta.mail.MessagingException;
import java.util.Timer;
import java.util.TimerTask;

@Route("/register")
@AnonymousAllowed
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@VaadinSessionScope
public class RegistrationForm extends VerticalLayout {

    TextField nameField;
    final TextField login;
    final PasswordField passwordField;
    EmailField emailField;
    final TextField phoneNum;
    final PasswordField confirmPassword;
    final UserDto userDto = new UserDto();
    final RegistrationMail registrationMail;

    public RegistrationForm(UserService userService,
                            AuthorityService authorityService,
                            @Autowired RegistrationMail registrationMail,
                            @Autowired SecurityService securityService) {
        this.registrationMail = registrationMail;

        if (!UserLogin.getCurrentUserLogin().equals("Anonymous")) {
            securityService.logout();
        }

        Binder<UserDto> binder = new Binder<>(UserDto.class);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        nameField = new TextField("Name");
        nameField.setClearButtonVisible(true);
        nameField.setMaxLength(50);
        add(nameField);

        emailField = new EmailField("Email address");
        emailField.setClearButtonVisible(true);
        emailField.setMaxLength(50);
        emailField.setRequiredIndicatorVisible(true);
        add(emailField);

        binder.forField(emailField)
                .withValidator(o -> !o.isBlank(), "Поле должно быть заполнено.")
                .withValidator(o -> !userService.isEmailExist(o),
                        "Пользователь с указанной почтой уже зарегистрирован. Укажите другую почту.")
                .withValidator(new EmailValidator(
                        "Формат почты указан неверно. Проверьте правильность написания почтового адреса."))
                .bind(UserDto::getEmail, UserDto::setEmail);

        phoneNum = new TextField("Phone number");
        phoneNum.setClearButtonVisible(true);
        phoneNum.setMaxLength(12);

        phoneNum.setHelperText("Формат: +79250816078. Вы можете не указывать телефон. Данное поле для учебных целей");
        RegexpValidator regexpValidator = new RegexpValidator("Неверноый формат номера", "^((8|\\+*)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        binder.forField(phoneNum)
                .withValidator(regexpValidator)
                .bind(UserDto::getName, UserDto::setName);
        add(phoneNum);

        login = new TextField("Login");
        login.setRequiredIndicatorVisible(true);
        login.setClearButtonVisible(true);
        login.setMinLength(3);
        login.setHelperText("Обязательное поле");
        add(login);

        binder.forField(login)
                .withValidator(o -> !userService.isLoginExist(o),
                        "Логин занят. Придумайте другой.")
                .withValidator(o -> !o.isBlank(), "Поле должно быть заполнено.")
                .bind(UserDto::getName, UserDto::setName);

        passwordField = new PasswordField("Password");
        passwordField.setClearButtonVisible(true);
        add(passwordField);

        confirmPassword = new PasswordField("Confirm the password");
        confirmPassword.setClearButtonVisible(true);
        add(confirmPassword);

        Button clear = new Button("Clear", event -> {
            clearField();
        });
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> cancel.getUI()
                .ifPresent(ui -> ui.navigate(MainPage.class)));

        Button createAccount = new Button("Create account", event -> {
            binder.forField(nameField).bind(UserDto::getFirstName, UserDto::setFirstName);
            binder.forField(passwordField).bind(UserDto::getPassword, UserDto::setPassword);
            binder.forField(confirmPassword).bind(UserDto::getConfirmPassword, UserDto::setConfirmPassword);
            binder.forField(emailField).bind(UserDto::getEmail, UserDto::setEmail);

            binder.forField(login).bind(UserDto::getName, UserDto::setName);
            binder.forField(phoneNum).bind(UserDto::getPhoneNumber, UserDto::setPhoneNumber);

            try {
                binder.writeBean(userDto);
                checkRegistrationAndAddNewUser(userDto, userService);
                authorityService.addNewUserAuthority(new Authority(userDto.getName(), "USER"));
                log.debug("Новый пользователь: " + userDto);
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        });

        createAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        createAccount.addClickListener(e -> {
                    if (!userDto.getEmail().isBlank()) {
                        sendAboutRegistrationMail();
                        createAccount.getUI()
                                .ifPresent(ui -> ui.navigate(ConfirmRegistrationPage.class));
                    } else {
                        createAccount.getUI()
                                .ifPresent(ui -> ui.navigate(WorkSpacePage.class));
                    }
                }
        );

        HorizontalLayout buttonLayout = new HorizontalLayout(createAccount, clear, cancel);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setPadding(true);
        add(buttonLayout);

        Text warning = new Text(
                "Настоящее приложение является учебным.. " +
                        "Владелец сайта не является оператором персональных данных."
        );
        add(warning);
    }

    private void sendAboutRegistrationMail() {
        Runnable task = () -> {
            try {
                registrationMail.sendMessage(userDto);
            } catch (ReadPropertiesException | MessagingException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thread.interrupt();
            }
        }, 10000);
    }

    private void clearField() {
        passwordField.setValue("");
        confirmPassword.setValue("");
        emailField.setValue("");
        phoneNum.setValue("");
        nameField.setValue("");
        passwordField.setValue("");
    }

    private void checkRegistrationAndAddNewUser(UserDto userDto, UserService userService) {
        final String PASSWORD_MISTAKE = "Пароли не совпадают. Проверьте правильность ввода.";
        final String LOGIN_MISTAKE = "Обязательное поле login пустое. Проверьте правильность ввода.";

        if (!userDto.getPassword().equals(userDto.getConfirmPassword()) && userDto.getName().isBlank()) {
            getUI().ifPresent(ui -> ui.navigate(MistakePage.class, (PASSWORD_MISTAKE + LOGIN_MISTAKE)));
        } else if (userDto.getPassword().isBlank() && userDto.getName().isBlank()) {
            getUI().ifPresent(ui -> ui.navigate(MistakePage.class, (PASSWORD_MISTAKE + LOGIN_MISTAKE)));
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            getUI().ifPresent(ui -> ui.navigate(MistakePage.class,
                    PASSWORD_MISTAKE));
        } else if (userDto.getName().isBlank()) {
            getUI().ifPresent(ui -> ui.navigate(MistakePage.class,
                    LOGIN_MISTAKE));
        } else {
            userService.addNewUser(userDto);
        }
    }
}
