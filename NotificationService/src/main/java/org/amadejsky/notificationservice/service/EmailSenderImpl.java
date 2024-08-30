package org.amadejsky.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.amadejsky.notificationservice.dto.EmailDto;
import org.amadejsky.notificationservice.dto.NotificationDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmails(NotificationDto notificationDto) {
        String title = "Remember about course "+ notificationDto.getCourseName();
        StringBuilder content = createEmailContent(notificationDto);
        notificationDto.getEmails().forEach(email-> {
            try {
                sendEmail(email,title,content.toString());
            } catch (MessagingException e) {
                log.error("Notification does not sent! ",e);
            }
        });
    }

    private static StringBuilder createEmailContent(NotificationDto notificationDto) {
        StringBuilder content = new StringBuilder();
        content.append("Course: ");
        content.append(notificationDto.getCourseName());
        content.append(" starts: ");
        content.append(notificationDto.getCourseStartDate());
        content.append("\n");
        content.append("Course description: ");
        content.append(notificationDto.getCourseDescription());
        return content;
    }

    @Override
    public void sendEmail(EmailDto emailDto) throws MessagingException {
        sendEmail(emailDto.getTo(),emailDto.getTitle(),emailDto.getContent());
    }

    private void sendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content,false);
        javaMailSender.send(mimeMessage);
    }
}
