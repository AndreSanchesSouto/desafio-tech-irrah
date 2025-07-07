package com.irrah.back_end.repositories;

import com.irrah.back_end.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    @Query(value = """
            SELECT * FROM chats WHERE user_common_id = :userId
            """,nativeQuery = true)
    Optional<ChatEntity> findByUserId(@Param("userId") UUID userId);

}
