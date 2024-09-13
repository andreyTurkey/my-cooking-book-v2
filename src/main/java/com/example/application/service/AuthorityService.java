package com.example.application.service;

import org.springframework.stereotype.Service;
import com.example.application.model.Authority;
import com.example.application.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void addNewUserAuthority(Authority authority) {
        authorityRepository.save(authority);
    }
}
