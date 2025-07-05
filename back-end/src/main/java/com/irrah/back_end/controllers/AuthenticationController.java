package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.authentication.AuthenticationLogin;
import com.irrah.back_end.dtos.authentication.AuthenticationResponse;
import com.irrah.back_end.dtos.user.RegisterUserDto;
import com.irrah.back_end.services.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDto request) {
        return this.service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationLogin request) {
        return this.service.login(request);
    }

}
