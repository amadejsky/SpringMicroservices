package org.amadejsky.publisher.controller;

import org.amadejsky.publisher.model.Notification;
import org.amadejsky.publisher.service.NotificationServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private final RabbitTemplate rabbitTemplate;
    private final NotificationServiceImpl notificationService;

    public MessageController(RabbitTemplate rabbitTemplate, NotificationServiceImpl notificationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationService = notificationService;
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

    @GetMapping("/notifyTask")
    public String getStudentFromAPI(@RequestParam Long studentId){
        notificationService.sendStudentNotification(studentId);
    return "Message has been sent to student of id: "+studentId;
    }

    // przyjmownaie żadnia z zewantrz, wywołąnie warsty seriwsowej, kominkacja i wrzucanie na rabita,
    // zrobić serwis oraz klase ktora implenetuje
}
