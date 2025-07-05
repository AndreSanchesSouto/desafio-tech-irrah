package com.irrah.back_end.services;

import com.irrah.back_end.dtos.authentication.AuthenticationLogin;
import com.irrah.back_end.dtos.authentication.AuthenticationResponse;
import com.irrah.back_end.dtos.user.RegisterUserDto;
import com.irrah.back_end.dtos.user.ResponseUserDto;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.DocumentType;
import com.irrah.back_end.enums.Role;
import com.irrah.back_end.enums.UserStatus;
import com.irrah.back_end.exceptions.RoleException;
import com.irrah.back_end.exceptions.UserException;
import com.irrah.back_end.infrastructure.security.TokenService;
import com.irrah.back_end.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<Void> register(RegisterUserDto request) {
        this.documentValidator(request.document());
        if (repository.findAll().isEmpty()) {
            return this.createUser(request, Role.ADMINER);
        }
        return this.createUser(request, Role.COMMON);
    }

    public void documentValidator(String document) {

    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationLogin request) {
        UserEntity user = findByDocument(request.document());
        String token = tokenService.generateToken(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(token, new ResponseUserDto(user)));
    }

    private ResponseEntity<Void> createUser(RegisterUserDto request, Role role) {
        this.validateUserData(request, role);

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setDocument(request.document());
        user.setStatus(UserStatus.ACTIVE.getUserStatus());
        user.setRole(role.getRole());
        this.repository.save(user);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public void validateUserData(RegisterUserDto request, Role role) {
        this.validateRole(role);
        this.userDocumentExists(request.document());
    }

    public Role validateRole(Role role) {
        for(Role r : Role.values()) {
            if(r == role) return role;
        }
        throw new RoleException("Nível de acesso do usuário não aceito");
    }

    public UserEntity findByDocument(String document) {
        return repository.findByDocument(document).orElseThrow(
                () -> new UserException("Núemero de documento não encontrado")
        );
    }

    public void userDocumentExists(String document) {

        if( repository.findByDocument(document).isPresent()) {
            throw new UserException("Documento já registrado");
        }
    }

}
