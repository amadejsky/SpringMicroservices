package org.amadejsky.notificationservice.service;

import jakarta.mail.MessagingException;
import org.amadejsky.notificationservice.dto.EmailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSenderTest {
    @Autowired
    EmailSender emailSender;
    @Test
    public void send_email_test() throws MessagingException {
        EmailDto emailDto = EmailDto.builder()
                        .to("adrian.madejski@vp.pl")
                                .title("Hello!")
                                        .content("Test of connection")
                                                .build();
        emailSender.sendEmail(emailDto);
    }
}
