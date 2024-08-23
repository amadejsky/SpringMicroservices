package org.amadejsky.publisher.service;

import org.amadejsky.publisher.model.Notification;
import org.amadejsky.publisher.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class NotificationServiceImpl implements NotificationService {
    public static final String URL = "http://localhost:8080/students/";
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public NotificationServiceImpl(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendStudentNotification(Long studentId) {
    Student student = restTemplate.exchange(URL +studentId,
                HttpMethod.GET, HttpEntity.EMPTY, Student.class).getBody();

        Notification notification = new Notification();
        notification.setEmail(student.getEmail());
        notification.setTitle("Hello!"+" "+student.getFirstName());
        notification.setBody("Nice to have you enrolled"+" "+student.getFirstName()+" "+student.getLastName());
            rabbitTemplate.convertAndSend("test",notification);
    }
}
