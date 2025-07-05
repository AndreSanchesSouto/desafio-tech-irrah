package com.irrah.back_end.dtos.user;
import com.irrah.back_end.enums.DocumentType;
import com.irrah.back_end.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterUserDto(

        @NotBlank(message = "Informe um nome")
        String name,
        @NotBlank(message = "Informe um documento")
        String document

) { }
