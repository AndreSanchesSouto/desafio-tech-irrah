package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.user.PatchUserDto;
import com.irrah.back_end.dtos.user.ResponseUserDto;
import com.irrah.back_end.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDto> patch(@PathVariable UUID id, @RequestBody PatchUserDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, request));
    }
}
