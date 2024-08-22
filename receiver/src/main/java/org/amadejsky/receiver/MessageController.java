package org.amadejsky.receiver;

import org.amadejsky.notification.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String receiveMessage(){
        Object message = rabbitTemplate.receiveAndConvert("test");
        if(message!=null)
            return "Message received succesfully, the content is: "+message.toString();
        return "There are no messages!";
    }

//    @RabbitListener(queues = "test")
//    public void listenerOfMessages(String message){
//        System.out.println(message);
//    }
    @GetMapping("/notification")
    public ResponseEntity<Notification> receivedNotification(){
        Object notification = rabbitTemplate.receiveAndConvert("test");
        if(notification instanceof Notification){
            return ResponseEntity.ok((Notification)notification);
        }
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/notification")
//    public ResponseEntity<Notification> receivedNotification() {
//        Message message = rabbitTemplate.receive("test");
//        if (message != null) {
//            Object notification = rabbitTemplate.getMessageConverter().fromMessage(message);
//            if (notification instanceof Notification) {
//                return ResponseEntity.ok((Notification) notification);
//            }
//        }
//        return ResponseEntity.noContent().build();
//    }

    @RabbitListener(queues = "test")
    public void listenerMessage(Notification notification){
        System.out.println(notification.getEmail()+" "+notification.getTitle()+" "+notification.getBody());
    }
}
