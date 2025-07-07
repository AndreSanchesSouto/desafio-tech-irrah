package com.irrah.back_end.workers;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
import com.irrah.back_end.queue.MessageQueue;
import com.irrah.back_end.repositories.MessageRepository;
import com.irrah.back_end.services.MessageService;
import com.irrah.back_end.websocket.MessageWebSocketController;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageWorker {
    @Autowired
    private MessageQueue messageQueue;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageWebSocketController webSocketController;

    @PostConstruct
    public void startWorker() {
        Thread workerThread = new Thread(() -> {
            while (true) {
                try {
                    MessageEntity message = messageQueue.dequeue();

                    this.setProcessingMessageStatus(message);
                    webSocketController.sendMessageStatusUpdate(new ResponseMessageDto(message));
                    // Simula envio da mensagem
                    Thread.sleep(2000); // tempo de processamento

                    UserEntity sender = message.getUserSender();
                    if (sender.getPlanType().equals(PlanType.PREPAID.getPlanType())) {
                        this.messageService.handlePrepaidMessage(sender, message);
                    } else {
                        this.messageService.handlePostpaidMessage(sender, message);
                    }
                    messageService.patchMessageStatus(message.getId(), MessageStatus.valueOf(message.getStatus()));

                    // Aqui você pode emitir um WebSocket para notificar o cliente
                    webSocketController.sendMessageStatusUpdate(new ResponseMessageDto(message));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        workerThread.start();
    }

    private void setProcessingMessageStatus(MessageEntity message) {
        System.out.println("Processando: " + message.getText());
        message.setStatus(MessageStatus.PROCESSING.getStatus());
        messageService.patchMessageStatus(message.getId(), MessageStatus.valueOf(message.getStatus()));
    }

}
