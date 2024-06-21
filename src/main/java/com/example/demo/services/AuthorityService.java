package com.example.demo.services;

import com.example.demo.models.Authority;
import com.example.demo.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority){
        return authorityRepository.save(authority);
    }
}
