package com.irrah.back_end.workers;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
import com.irrah.back_end.exceptions.MessageException;
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
                    Thread.sleep(2000);

                    this.setMessageStatus(message);

                    webSocketController.sendMessageStatusUpdate(new ResponseMessageDto(message));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                System.out.println("Erro no processamento da mensagem: " + e.getMessage());
            }}
        });
        workerThread.start();
    }

    private void setMessageStatus(MessageEntity message) {
        UserEntity sender = message.getUserSender();

        if(sender.getPlanType() == null) throw new MessageException("Selecione um plano para enviar uma mensagem");

        if (sender.getPlanType().equals(PlanType.PREPAID.getPlanType())) {
            this.messageService.handlePrepaidMessage(sender, message);
        } else {
            this.messageService.handlePostpaidMessage(sender, message);
        }
        System.out.println("message up");
        System.out.println(message);
        messageService.patchMessageStatus(message, message.getStatus());
    }

    private void setProcessingMessageStatus(MessageEntity message) {
        System.out.println("Processando: " + message.getText());
        message.setStatus(MessageStatus.PROCESSING.getStatus());
        messageService.patchMessageStatus(message, message.getStatus());
    }

}
