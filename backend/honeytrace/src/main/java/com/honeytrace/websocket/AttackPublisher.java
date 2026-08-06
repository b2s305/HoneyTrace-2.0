package com.honeytrace.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AttackPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public AttackPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(AttackMessage message) {

        messagingTemplate.convertAndSend(
                "/topic/attacks",
                message
        );

    }

}