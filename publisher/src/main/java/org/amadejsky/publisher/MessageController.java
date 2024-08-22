package org.amadejsky.publisher;

import org.amadejsky.notification.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String sendMessage(@RequestParam String message) {

        rabbitTemplate.convertAndSend("test", message);
        return "Message thrown on RabbitAMQP";
    }
    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification){
        rabbitTemplate.convertAndSend("test", notification);
        return "Notification sent!";
    }
}
