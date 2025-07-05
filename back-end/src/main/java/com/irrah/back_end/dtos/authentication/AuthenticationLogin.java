package com.irrah.back_end.dtos.authentication;

import jakarta.validation.constraints.NotNull;

public record AuthenticationLogin (
        @NotNull
        String document
){ }
