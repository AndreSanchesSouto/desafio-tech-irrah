package com.irrah.back_end.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String document;

    @Setter
    @Column(nullable = false)
    private String status;

    @Setter
    @Column(nullable = false)
    private String role;

    @Setter
    @Column(nullable = true)
    private String planType;

    @Setter
    @Column(nullable = true)
    private BigDecimal balance;

    @Setter
    @Column(nullable = true)
    private BigDecimal monthLimit;

    @Column(nullable = false)
    private LocalDate createdDt = LocalDate.now();

    @Setter
    @Column(nullable = true)
    private LocalDate removedDt;

}
