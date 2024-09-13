package com.example.application.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String firstName;

    String name;

    String password;

    String confirmPassword;

    String email;

    String phoneNumber;

    Boolean enabled;
}
