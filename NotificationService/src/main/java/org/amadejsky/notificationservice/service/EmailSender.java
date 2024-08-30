package org.amadejsky.notificationservice.service;

import org.amadejsky.notificationservice.dto.NotificationDto;

public interface EmailSender {
    void sendEmails(NotificationDto notificationDto);
}
