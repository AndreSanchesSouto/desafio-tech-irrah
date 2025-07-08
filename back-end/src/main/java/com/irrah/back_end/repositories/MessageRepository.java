package com.irrah.back_end.repositories;

import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {


    @Query(value = """
            SELECT m.* FROM messages m
            JOIN users u ON u.id = m.user_receiver_id
            ORDER BY m.created_at
            """, nativeQuery = true)
    MessageEntity findLastMessageFromUser(@Param("userId") UUID userId);

    @Query(value = """
            SELECT m.* FROM messages m
            JOIN chats c ON c.id = m.chat_id
            WHERE m.status IN ('delivered', 'read')
            AND c.id = :chatId
            ORDER BY m.created_at
            """, nativeQuery = true)
    List<MessageEntity> findByChatId(@Param("chatId") UUID chatId);
}
