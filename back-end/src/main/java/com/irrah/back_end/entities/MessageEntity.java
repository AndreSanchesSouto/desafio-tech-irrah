package com.irrah.back_end.entities;

import com.irrah.back_end.enums.MessagePriority;
import com.irrah.back_end.enums.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_sender_id", nullable = false)
    private UserEntity userSender;

    @ManyToOne
    @JoinColumn(name = "user_receiver_id", nullable = false)
    private UserEntity userReceiver;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String priorityLevel;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate createdAt = LocalDate.now();

}
