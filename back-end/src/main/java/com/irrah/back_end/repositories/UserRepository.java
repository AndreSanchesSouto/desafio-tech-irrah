package com.irrah.back_end.repositories;

import com.irrah.back_end.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = """
            SELECT * FROM users WHERE document = :document
            """, nativeQuery = true)
    Optional<UserEntity> findByDocument(@Param("document") String document);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserDetails> findUserDetailsById(@Param("id") UUID id);

    @Query(value = """
            SELECT * FROM users WHERE name = :name
            """, nativeQuery = true)
    Optional<UserEntity> findByName(@Param("name") String name);
}
