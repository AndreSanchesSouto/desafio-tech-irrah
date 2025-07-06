package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.authentication.LoginAuthentication;
import com.irrah.back_end.dtos.authentication.ResponseAuthentication;
import com.irrah.back_end.dtos.user.RegisterUserDto;
import com.irrah.back_end.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseAuthentication> login(@RequestBody @Valid LoginAuthentication request) {
        return this.service.login(request);
    }

}
