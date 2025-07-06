package com.irrah.back_end.services;

import com.irrah.back_end.dtos.message.RequestMessageDto;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessagePriority;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
import com.irrah.back_end.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private UserService userService;

    public MessageEntity getLastMessageFromUser(UUID userId) {
        return this.repository.findLastMessageFromUser(userId);
    }

    public ResponseMessageDto post(RequestMessageDto request) {
        UserEntity user = this.userService.findCurrentUser();
        MessageEntity message = createBaseMessage(request, user);

        if (user.getPlanType().equals(PlanType.PREPAID.getPlanType())) {
            handlePrepaidMessage(user, message);
        } else {
            handlePostpaidMessage(user, message);
        }

        this.repository.save(message);
        return new ResponseMessageDto(message);
    }

    private MessageEntity createBaseMessage(RequestMessageDto request, UserEntity sender) {
        MessagePriority priority = MessagePriority.fromType(request.priority());

        MessageEntity message = new MessageEntity();
        message.setPriorityLevel(priority.getType());
        message.setPrice(priority.getPrice());
        message.setText(request.content());
        message.setType(PlanType.valueOf(request.userPlanType()).getPlanType());
        message.setUserReceiver(this.userService.findById(request.recipientId()));
        message.setUserSender(sender);

        return message;
    }

    private void handlePrepaidMessage(UserEntity user, MessageEntity message) {
        if (this.userService.canPayMessageCost(user, message)) {
            message.setStatus(MessageStatus.QUEUED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }

    private void handlePostpaidMessage(UserEntity user, MessageEntity message) {
        if (this.userService.hasMonthLimit(user, message)) {
            message.setStatus(MessageStatus.QUEUED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }
}
