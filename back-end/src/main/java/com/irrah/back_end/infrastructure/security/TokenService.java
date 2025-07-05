package com.irrah.back_end.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.exceptions.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity employee) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(employee.getId().toString())
                    .withExpiresAt(generateTimer())
                    .sign(algorithm);
        } catch (JWTCreationException jwtError) {
            throw new JWTCreationException("Error generating token", jwtError);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException jwtError) {
            throw new TokenException("Erro na criação do token " + jwtError);
        }
        catch (TokenExpiredException expired) {
            throw new TokenException("Token expirado " + expired);
        }
    }

    private Instant generateTimer() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
