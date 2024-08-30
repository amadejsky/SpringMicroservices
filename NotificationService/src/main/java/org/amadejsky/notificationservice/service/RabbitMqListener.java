package org.amadejsky.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.amadejsky.notificationservice.dto.NotificationDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListener {

    private final EmailSender emailSender;

    public RabbitMqListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues="enrollment_finish")
    public void handleFinishEnroll(NotificationDto notificationDto){
        log.info(notificationDto.toString());
    }
}
