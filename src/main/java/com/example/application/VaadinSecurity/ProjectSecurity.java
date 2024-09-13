package com.example.application.VaadinSecurity;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.application.service.JpaUserDetailsService;
import com.example.application.vaadinPart.LoginView;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProjectSecurity extends VaadinWebSecurity {

    private final JpaUserDetailsService jdbcUserDetailsManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers("/", "/**")
                .permitAll();
        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService users() {
        return jdbcUserDetailsManager;
    }
}
