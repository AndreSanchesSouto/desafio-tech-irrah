package com.irrah.back_end.repositories;

import com.irrah.back_end.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query(value = """
            SELECT p.* FROM payments p
            JOIN users u ON u.id = p.client_id
            WHERE u.id = :userId
            """, nativeQuery = true)
    List<PaymentEntity> findByUserId(@Param("userId") UUID userId);

}
    