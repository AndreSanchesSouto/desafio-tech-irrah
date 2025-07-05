package com.irrah.back_end.controller;

import com.irrah.back_end.dto.user.RegisterUserDto;
import com.irrah.back_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterUserDto request) {
        return this.service.register(request);
    }

}
