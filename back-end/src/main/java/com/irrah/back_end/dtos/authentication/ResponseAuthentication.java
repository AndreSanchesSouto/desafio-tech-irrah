package com.irrah.back_end.dtos.authentication;

import com.irrah.back_end.dtos.user.ResponseUserDto;

public record ResponseAuthentication(
        String token,
        ResponseUserDto client
){ }
