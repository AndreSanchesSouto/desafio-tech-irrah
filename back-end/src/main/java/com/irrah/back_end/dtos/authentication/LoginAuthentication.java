package com.irrah.back_end.dtos.authentication;

import jakarta.validation.constraints.NotNull;

public record LoginAuthentication(
        @NotNull
        String document
){ }
