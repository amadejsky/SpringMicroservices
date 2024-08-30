package org.amadejsky.notificationservice.service;

import jakarta.mail.MessagingException;
import org.amadejsky.notificationservice.dto.EmailDto;
import org.amadejsky.notificationservice.dto.NotificationDto;

public interface EmailSender {
    void sendEmails(NotificationDto notificationDto);
    public void sendEmail(EmailDto emailDto) throws MessagingException;


}
