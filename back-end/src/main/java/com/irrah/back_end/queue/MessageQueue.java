package com.irrah.back_end.queue;

import com.irrah.back_end.entities.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Component
public class MessageQueue {
    private final BlockingQueue<MessageEntity> queue = new LinkedBlockingQueue<>();

    public void enqueue(MessageEntity message) throws InterruptedException {
        queue.put(message);
    }

    public MessageEntity dequeue() throws InterruptedException {
        return queue.take();
    }
}
