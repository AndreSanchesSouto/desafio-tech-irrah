package com.irrah.back_end.repositories;

import com.irrah.back_end.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
}
