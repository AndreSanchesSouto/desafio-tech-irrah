package com.irrah.back_end.repositories;

import com.irrah.back_end.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {


    @Query(value = """
            SELECT m.* FROM messages m
            JOIN users u ON u.id = m.user_receiver_id
            ORDER BY m.created_at
            """, nativeQuery = true)
    MessageEntity findLastMessageFromUser(@Param("userId") UUID userId);
}
