package com.example.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.application.model.CustomUserDetails;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;

import java.util.function.Supplier;

@Primary
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Пользователь не найден");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(s);
        return new CustomUserDetails(user);
    }
}
