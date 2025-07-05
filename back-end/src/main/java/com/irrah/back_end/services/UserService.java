package com.irrah.back_end.services;

import com.irrah.back_end.dtos.authentication.AuthenticationLogin;
import com.irrah.back_end.dtos.authentication.AuthenticationResponse;
import com.irrah.back_end.dtos.user.PatchUserDto;
import com.irrah.back_end.dtos.user.RegisterUserDto;
import com.irrah.back_end.dtos.user.ResponseUserDto;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.Role;
import com.irrah.back_end.enums.UserStatus;
import com.irrah.back_end.exceptions.RoleException;
import com.irrah.back_end.exceptions.UserException;
import com.irrah.back_end.infrastructure.security.TokenService;
import com.irrah.back_end.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;
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

    private ResponseEntity<Void> createUser(RegisterUserDto request, Role role) {
        this.validateUserDataCreation(request, role);

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setDocument(request.document());
        user.setStatus(UserStatus.ACTIVE.getUserStatus());
        user.setRole(role.getRole());
        this.repository.save(user);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public void validateUserDataCreation(RegisterUserDto request, Role role) {
        this.validateRole(role);
        this.userDocumentExists(request.document());
        this.userNameExists(request.name());
    }

    public Role validateRole(Role role) {
        for(Role r : Role.values()) {
            if(r == role) return role;
        }
        throw new RoleException("Nível de acesso do usuário não aceito");
    }

    public void userNameExists(String name) {
        if( repository.findByName(name).isPresent()) {
            throw new UserException("Nome já registrado");
        }
    }

    public void userDocumentExists(String document) {
        if( repository.findByDocument(document).isPresent()) {
            throw new UserException("Documento já registrado");
        }
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationLogin request) {
        UserEntity user = findByDocument(request.document());
        String token = tokenService.generateToken(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(token, new ResponseUserDto(user)));
    }

    public UserEntity findByDocument(String document) {
        return repository.findByDocument(document).orElseThrow(
                () -> new UserException("Núemero de documento não encontrado")
        );
    }

    public ResponseUserDto getById(UUID id) {
        UserEntity user = this.findById(id);
        return new ResponseUserDto(user);
    }

    public UserEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(
                () -> new UserException("Usuário não encontrado")
        );
    }

    public UserEntity findByName(String name) {
        return this.repository.findByName(name).orElseThrow(
                () -> new UserException("Nome de usuário não encontrado")
        );
    }

    public ResponseUserDto patch(UUID id, PatchUserDto request) {
        UserEntity user = this.findById(id);
        UserEntity currentUser = this.getCurrentUser();

        if(!user.getId().equals(currentUser.getId())) {
            throw new UserException("Você só pode alterar seus próprios dados");
        }

        this.validateEditionData(id, request);
        this.validateStatus(UserStatus.valueOf(request.status()));
        this.updateUser(user, request);

        return new ResponseUserDto(user);
    }

    public UserStatus validateStatus(UserStatus status) {
        for(UserStatus us : UserStatus.values()) {
            if(us == status) return status;
        }
        throw new RoleException("Status de usuário não aceito");
    }

    private void updateUser(UserEntity user, PatchUserDto request) {
        user.setName(request.name());
        user.setStatus(request.status());
        user.setBalance(request.balance().toString());
        user.setPlanType(request.planType());
        user.setMonthLimit(request.number().toString());
        user.setDocument(request.documentId());

        this.repository.save(user);
    }

    public void validateEditionData(UUID userId, PatchUserDto request) {
        if (request.documentId() != null) {
            Optional<UserEntity> existingUser = repository.findByDocument(request.documentId());
            if(existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                throw new UserException("O documento já está em uso");
            }
        }

        if (request.name() != null) {
            Optional<UserEntity> existingUser = repository.findByName(request.name());
            if(existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                throw new UserException("Esse nome já está em uso");
            }
        }

    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        return this.findByName(authenticatedUsername);
    }

}
