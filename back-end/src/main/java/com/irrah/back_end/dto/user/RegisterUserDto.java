package com.irrah.back_end.dto.user;
import com.irrah.back_end.enums.DocumentType;
import com.irrah.back_end.enums.PlanType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterUserDto(

        @NotNull(message = "Informe um nome")
        String name,
        @NotNull(message = "Informe um documento")
        String document,
        String balance,
        String limit,
        String number,
        @NotNull(message = "Informe qual plano deseja")
        String planType

) { }
