package com.example.application.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.application.dto.UserDto;
import com.example.application.model.User;

@Slf4j
@Component
public class UserMapper {

    private final PasswordEncoder encoder;

    public UserMapper(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User getUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setUsername(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEnabled(true);
        return user;
    }
}
