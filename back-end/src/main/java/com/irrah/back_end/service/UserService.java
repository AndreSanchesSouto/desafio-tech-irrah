package com.irrah.back_end.service;

import com.irrah.back_end.dto.user.RegisterUserDto;
import com.irrah.back_end.entity.UserEntity;
import com.irrah.back_end.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserService {

    @Autowired
    UserRepository repository;

    public ResponseEntity<Void> register(RegisterUserDto request) {
        if (repository.findAll().isEmpty()) {
            return this.createAdminer(request);
        }
    }

    private ResponseEntity<Void> createAdminer(RegisterUserDto request) {
        UserEntity user = new UserEntity();

        user.se
        request.

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
