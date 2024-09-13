package com.example.application.VaadinSecurity;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@VaadinSessionScope
public class UserLogin {

    private static Authentication authentication;

    public UserLogin(Authentication authentication) {
        this.authentication = authentication;
    }

    public static   String getCurrentUserLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "Anonymous";
    }
}
