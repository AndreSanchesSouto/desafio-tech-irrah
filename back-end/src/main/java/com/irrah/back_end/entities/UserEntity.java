package com.irrah.back_end.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter

@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
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

    @Column(nullable = true)
    private BigDecimal balance;

    @Column(nullable = true)
    private BigDecimal monthLimit;

    @Column(nullable = false)
    private LocalDate createdDt = LocalDate.now();

    @Setter
    @Column(nullable = true)
    private LocalDate removedDt;



    public void setBalance(String balance) {
        BigDecimal actualValue = new BigDecimal(balance);
        this.balance = actualValue.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMonthLimit(String setMonthLimit) {
        BigDecimal actualValue = new BigDecimal(setMonthLimit);
        this.balance = actualValue.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role.toUpperCase()) {
            case "ADMINER" -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMINER"),
                    new SimpleGrantedAuthority("ROLE_COMMON")
            );
            default -> List.of(new SimpleGrantedAuthority("ROLE_COMMON"));
        };
    }

    @Override
    public String getPassword() {
        return this.getDocument();
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

}
