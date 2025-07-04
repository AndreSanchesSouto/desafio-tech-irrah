package com.irrah.back_end.controller;

import com.irrah.back_end.dto.user.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired


    @PostMapping("/register")
    public void register(@RequestBody @Validated RegisterUserDto request) {

    }

}
